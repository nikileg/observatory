package observatory.visualization.interpolation

trait Interpolation[T] {
  protected val _div: (T, T) => T
  protected implicit val numeric$T$0: Numeric[T]

  /**
    * See <a href="https://en.wikipedia.org/wiki/Linear_interpolation">Linear interpolation</a> on wiki
    */
  def linear(point1: (T, T), point2: (T, T), at: T): T = {
    require(point1._1 != point2._1 || point1._2 == point2._2,
      "Points with same abscissa can't have different ordinate")
    import numeric$T$0._

    if (isSamePoints(point1, point2, at)) return point1._2 // or point2._2 , doesn't matter
    val Seq(left, right) = Seq(point1, point2).sortBy(_._1)
    val num1 = left._2 * (right._1 - at)
    val num2 = right._2 * (at - right._1)
    val den = right._1 - left._1
    _div(num1 + num2, den)
  }

  private def isSamePoints(point1: (T, T), point2: (T, T), at: T): Boolean = {
    point1._1 == point2._1 && point2._2 == at
    // supposed that point1._2 == point2._2 already checked
  }
}

object Interpolation {
  def integralInterpolation[T: Integral]: Interpolation[T] = new Interpolation[T] {
    override protected implicit val numeric$T$0: Numeric[T] = integral$T$0
    override protected val _div: (T, T) => T = integral$T$0.quot
  }

  def fractionalInterpolation[T: Fractional]: Interpolation[T] = new Interpolation[T] {
    override protected implicit val numeric$T$0: Numeric[T] = fractional$T$0
    override protected val _div: (T, T) => T = fractional$T$0.div
  }

  val doubleInterpolation: Interpolation[Double] = fractionalInterpolation[Double]
}
