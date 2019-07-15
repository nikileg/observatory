package observatory.visualization.interpolation

class InterpolationService {
  //  /**
  //    * See <a href="https://en.wikipedia.org/wiki/Linear_interpolation">Linear interpolation</a> on wiki
  //    */
  //  def linear2[X, Y](point1: (X, Y), point2: (X, Y), at: X)
  //                  (implicit ev$X: Numeric[X], ev$Y: Fractional[Y], ev$conforms: X => Y): Y = {
  //    import ev$Y._
  //    import ev$X._
  //
  //    val Seq(left, right) = Seq(point1, point2).sortBy[X](_._1)
  //
  //    val num: Y = left._2 * (right._1 - at) + right._2 * (at - left._1)
  //    val denum: Y = right._1 - left._1
  //    num / denum
  //  }

  /**
    * See <a href="https://en.wikipedia.org/wiki/Linear_interpolation">Linear interpolation</a> on wiki
    */
  def linearFract[X](left: (X, X), right: (X, X), at: X)
                    (implicit ev$X: Fractional[X]): X = {
    import ev$X._
    require(left._1 != right._1 || left._2 == right._2)

    if (left._1 == right._1 && right._2 == at) return left._2

    val num1 = left._2 * (right._1 - at)
    val num2 = right._2 * (at - left._1)
    val den = right._1 - left._1
    (num1 + num2) / den
  }

  /**
    * See <a href="https://en.wikipedia.org/wiki/Linear_interpolation">Linear interpolation</a> on wiki
    */
  def linearInt[X](left: (X, X), right: (X, X), at: X)
                    (implicit ev$X: Integral[X]): X = {
    import ev$X._
    require(left._1 != right._1 || left._2 == right._2)

    if (at == right._1 && right._1 == left._1) return left._2

    val num1 = left._2 * (right._1 - at)
    val num2 = right._2 * (at - left._1)
    val den = right._1 - left._1
    (num1 + num2) / den
  }
}
