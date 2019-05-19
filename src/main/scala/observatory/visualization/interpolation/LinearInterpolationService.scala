package observatory.visualization.interpolation

/**
  * See https://en.wikipedia.org/wiki/Linear_interpolation
  */
class LinearInterpolationService {

  def singleArgument[T](x: (T, T),
                        y: (T, T),
                        coordinate: T)
                       (implicit evidence: Fractional[T]): T = {
    import evidence._

    val num: T = y._1 * (x._2 - coordinate) + y._2 * (coordinate - x._1)
    val denum: T = x._2 - x._1
    num / denum
  }
}
