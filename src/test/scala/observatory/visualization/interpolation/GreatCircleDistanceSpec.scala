package observatory.visualization.interpolation

import observatory.Location
import org.scalatest.{Matchers, Outcome, PropSpec}
import org.scalatest.prop.{GeneratorDrivenPropertyChecks, PropertyChecks}
import observatory.generator.LocationGenerator

class GreatCircleDistanceSpec extends PropSpec with GeneratorDrivenPropertyChecks with Matchers {
  import LocationGenerator.arbLocation

  override protected def withFixture(test: NoArgTest): Outcome = {
    super.withFixture(test) match {
      case other =>
        distanceService = new GreatCircleDistance(radius, eta)
        other
    }
  }

  property("Circle distance should be greater than calculated by straight line") {
    forAll { (location1: Location, location2: Location) =>
      val circle = distanceService.distance(location1, location2)
      val line = straightDistance(location1, location2)
      circle should be >= line
    }
  }

  private def straightDistance(point1: Location, point2: Location): BigDecimal = {
    import math.{sqrt, pow}
    val (x1, y1, z1) = polarToEuclidean(point1)
    val (x2, y2, z2) = polarToEuclidean(point2)

    sqrt(
      pow(x1 - x2, 2) +
        pow(y1 - y2, 2) +
        pow(z1 - z2, 2)
    )
  }

  //unstable
  private def polarToEuclidean(location: Location): (Double, Double, Double) = {
    import math.{sin, cos}
    val latR = location.lat.toRadians
    val lonR = location.lon.toRadians
    (
      cos(latR) * cos(lonR),
      cos(latR) * sin(lonR),
      sin(latR)
    )
  }

  private val radius = 1
  private val eta = 1e-4
  private var distanceService = new GreatCircleDistance(radius, eta)
}
