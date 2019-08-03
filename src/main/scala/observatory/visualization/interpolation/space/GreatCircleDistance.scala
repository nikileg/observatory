package observatory.visualization.interpolation.space

import observatory.Location
import org.apache.commons.math.util.{MathUtils => MU}

class GreatCircleDistance(val radius: BigDecimal, eta: Double) {

  /**
    * See <a href="https://en.wikipedia.org/wiki/Great-circle_distance">https://en.wikipedia.org/wiki/Great-circle_distance</a>
    *
    * @param location1 polar coordinates on the sphere (in degrees)
    * @param location2 polar coordinates on the sphere (in degrees)
    */
  def distance(location1: Location, location2: Location): BigDecimal = {
    radius * deltaSigma(location1, location2)
  }

  /**
    * See <a href="https://en.wikipedia.org/wiki/Great-circle_distance">https://en.wikipedia.org/wiki/Great-circle_distance</a>
    *
    * @param location1 polar coordinates on the sphere (in degrees)
    * @param location2 polar coordinates on the sphere (in degrees)
    */
  private def deltaSigma(location1: Location, location2: Location): BigDecimal = {
    import math.{Pi, abs, acos, cos, sin}

    if (location1 == location2) return 0
    val Location(lat1, lon1) = location1
    val Location(lat2, lon2) = location2
    if (isAntipodes(location1, location2)) return Pi

    val Seq(lat1R, lon1R, lat2R, lon2R) = Seq(lat1, lon1, lat2, lon2).map(_.toRadians)
    val sins = sin(lat1R) * sin(lat2R)
    val coses = cos(lat1R) * cos(lat2R) * cos(abs(lon2R - lon1R))
    acos(sins + coses)
  }


  private def isAntipodes(location1: Location, location2: Location): Boolean = {
    // (φ, θ) == (−φ, θ ± 180°)
    MU.equals(location1.lat, -location2.lat, eta) && (
      MU.equals(location1.lon, location2.lon + 180.0, eta)
        || MU.equals(location1.lon, location2.lon - 180.0, eta)
      )
  }
}
