package observatory.visualization.interpolation

import org.scalatest.{Matchers, PropSpec}
import org.scalatest.prop.GeneratorDrivenPropertyChecks

class InterpolationServiceSpec extends PropSpec with GeneratorDrivenPropertyChecks with Matchers {

  property("Interpolated point should lie between source points") {
    forAll { (x1: Int, x2: Int, y1: Int, y2: Int, at: Int) =>
      val (p0, p1) = generateTwoFractionalSortedPoints(x1, x2, y1, y2)
      whenever(p0._1 < at && at < p1._1) {

        val resulting = service.linearFract(p0, p1, at.toDouble)

        resulting shouldBe >= (math.min(p0._2, p1._2))
        resulting shouldBe <= (math.max(p0._2, p1._2))
      }
    }
  }

  property("Interpolated point with *Int method should lie between source points") {
    forAll { (x1: Int, x2: Int, y1: Int, y2: Int, at: Int) =>
      val (p0, p1) = generateTwoIntegralSortedPoints(x1, x2, y1, y2)
      whenever(p0._1 < at && at < p1._1) {

        val resulting = service.linearInt(p0, p1, at.toLong)

        resulting shouldBe >= (math.min(p0._2, p1._2))
        resulting shouldBe <= (math.max(p0._2, p1._2))
      }
    }
  }

  property("Equal source points, when interpolated at same coordinates, should produce same point"){
    forAll{ (x: Int, y: Int) =>
      val p = (x.toLong, y.toLong)
      val result = service.linearInt(p, p, p._1)

      result shouldEqual p._2
    }
  }

  private val service = new InterpolationService
  private type PointFract = (Double, Double)
  private type PointLong = (Long, Long)

  private def generateTwoFractionalSortedPoints(x1: Int, x2: Int, y1: Int, y2: Int): (PointFract, PointFract) = {
    val Seq(xSmaller, xLarger) = Seq(x1, x2).sorted
    (
      (xSmaller.toDouble, y1.toDouble),
      (xLarger.toDouble, y2.toDouble)
    )
  }

  private def generateTwoIntegralSortedPoints(x1: Int, x2: Int, y1: Int, y2: Int): (PointLong, PointLong) = {
    val Seq(xSmaller, xLarger) = Seq(x1, x2).sorted
    (
      (xSmaller.toLong, y1.toLong),
      (xLarger.toLong, y2.toLong)
    )
  }
}
