package observatory.visualization.interpolation

import org.scalatest.prop.GeneratorDrivenPropertyChecks
import org.scalatest.{Matchers, PropSpec}

class InterpolationServiceSpec extends PropSpec with GeneratorDrivenPropertyChecks with Matchers {

  property("Interpolated point should lie between source points") {
    forAll { (x1: Short, x2: Short, y1: Short, y2: Short, at: Short) =>
      val (p0, p1) = generateTwoFractionalSortedPoints(x1, x2, y1, y2)
      whenever(p0._1 < at && at < p1._1) {
        val resulting = service.linearFract(p0, p1, at.toDouble)
        val leftWall = math.min(p0._2, p1._2)
        val rightWall = math.max(p0._2, p1._2)

        resulting shouldBe >=(leftWall)
        resulting shouldBe <=(rightWall)
      }
    }
  }

  property("Interpolated point with *Int method should lie between source points") {
    forAll { (x1: Int, x2: Int, y1: Int, y2: Int, at: Int) =>
      val (p0, p1) = generateTwoIntegralSortedPoints(x1, x2, y1, y2)
      whenever(p0._1 < at && at < p1._1) {
        val resulting = service.linearInt(p0, p1, at.toLong)
        val leftWall = math.min(p0._2, p1._2)
        val rightWall = math.max(p0._2, p1._2)

        resulting shouldBe >=(leftWall)
        resulting shouldBe <=(rightWall)
      }
    }
  }

  property("Equal source points, when interpolated at same coordinates, should produce same point") {
    forAll { (x: Int, y: Int) =>
      val p = (x.toLong, y.toLong)
      val result = service.linearInt(p, p, p._1)

      result shouldEqual p._2
    }
  }

  private val service = new InterpolationService
  private type PointFract = (Double, Double)
  private type PointLong = (Long, Long)
  private val eta = 1e-4

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
