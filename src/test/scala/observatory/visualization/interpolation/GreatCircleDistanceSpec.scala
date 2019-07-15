package observatory.visualization.interpolation

import org.scalatest.{Matchers, Outcome, PropSpec}
import org.scalatest.prop.GeneratorDrivenPropertyChecks

class GreatCircleDistanceSpec extends PropSpec with GeneratorDrivenPropertyChecks with Matchers {

  override protected def withFixture(test: NoArgTest): Outcome = {
    super.withFixture(test) match {
      case other =>
        distanceService = new GreatCircleDistance(radius, eta)
        other
    }
  }

  property("Circle distance should be greater than calculated by straight line") {
    forAll { (point1: (Double, Double), point2: (Double, Double)) =>

      whenever(){
        val circle = distanceService.distance(point1, point2)
        val line = straightDistance(point1, point2)
        val condition = circle >= line
        condition shouldBe true
      }
    }
  }

  private def straightDistance(point1: (Double, Double), point2: (Double, Double)) = {
    import math.{sqrt, pow}
    val (x1, y1, z1) = polarToEuclidean(point1)
    val (x2, y2, z2) = polarToEuclidean(point2)

    sqrt(
      pow(x1 - x2, 2) +
        pow(y1 - y2, 2) +
        pow(z1 - z2, 2)
    )
  }

  private def polarToEuclidean(degrees: (Double, Double)): (Double, Double, Double) = {
    import math.{sin, cos}
    val latR = degrees._1.toRadians
    val lonR = degrees._2.toRadians
    (
      sin(latR) * cos(lonR),
      sin(latR) * sin(lonR),
      cos(lonR)
    )
  }

  private val radius = 1
  private val eta = 1e-4
  private var distanceService = new GreatCircleDistance(radius, eta)
}
