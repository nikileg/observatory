package observatory.visualization.interpolation

class GreatCircleDistance(val radius: BigDecimal, eta: Double) {

  /**
    * See <a href="https://en.wikipedia.org/wiki/Great-circle_distance">https://en.wikipedia.org/wiki/Great-circle_distance</a>
    *
    * @param point1 polar coordinates on the sphere (in degrees)
    * @param point2 polar coordinates on the sphere (in degrees)
    */
  def deltaSigma(point1: (Double, Double), point2: (Double, Double)): Double = {
    import math.{abs, acos, cos, sin, Pi}

    if (point1 == point2) return 0
    val (lat1, lon1) = point1
    val (lat2, lon2) = point2
    if (lat1 + lat2 < eta &&
      (lon1 - lon2 + 180 < eta || lon1 - lon2 - 180 < eta)) return Pi

    val Seq(lat1R, lon1R, lat2R, lon2R) = Seq(lat1, lon1, lat2, lon2).map(_.toRadians)
    val sins = sin(lat1R) * sin(lat2R)
    val coses = cos(lat1R) * cos(lat2R) * cos(abs(lon2R - lon1R))
    acos(sins + coses)
  }

  /**
    * See <a href="https://en.wikipedia.org/wiki/Great-circle_distance">https://en.wikipedia.org/wiki/Great-circle_distance</a>
    *
    * @param point1 polar coordinates on the sphere (in degrees)
    * @param point2 polar coordinates on the sphere (in degrees)
    */
  def distance(point1: (Double, Double), point2: (Double, Double)): BigDecimal = {
    radius * deltaSigma(point1, point2)
  }
}
