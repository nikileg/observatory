package observatory.visualization.interpolation

import observatory.Location
import observatory.generator.LocationGenerator
import observatory.visualization.space.GreatCircleDistance
import org.apache.commons.math.util.{MathUtils => MU}
import org.scalatest.prop.GeneratorDrivenPropertyChecks
import org.scalatest.{Matchers, PropSpec}

class GreatCircleDistanceSpec extends PropSpec with GeneratorDrivenPropertyChecks with Matchers {
  import LocationGenerator.arbLocation

  property("Circle distance should be greater than calculated by straight line") {
    forAll { (location1: Location, location2: Location) =>
      val circle = distanceService.distance(location1, location2)
      val line = straightDistance(location1, location2)

      circle should be >= (line - eta)
      //      /*
      //        Couldn't configure tolerance for floating point comparison
      //        instead using this crutch for `circle should be >= line +- tolerance`
      //       */
      //      assert((circle - line).abs <= tolerance || circle >= line)
    }
  }

  property("Circle distance is always <= pi * radius") {
    forAll { (location1: Location, location2: Location) =>
      val distance = distanceService.distance(location1, location2)
      distance should be <= (PI_R)
    }
  }

  private def straightDistance(location1: Location, location2: Location): BigDecimal = {
    val point1 = polarToEuclidean(location1)
    val point2 = polarToEuclidean(location2)

    MU.distance(point1, point2)
  }

  //unstable
  private def polarToEuclidean(location: Location): Array[Double] = {
    import math.{cos, sin}
    val latR = location.lat.toRadians
    val lonR = location.lon.toRadians
    Array(
      cos(latR) * cos(lonR),
      cos(latR) * sin(lonR),
      sin(latR)
    )
  }

  private val radius = 1
  private val eta = 1e-4
  private val distanceService = new GreatCircleDistance(radius, eta)
  private val PI_R = BigDecimal(radius * math.Pi)
}
