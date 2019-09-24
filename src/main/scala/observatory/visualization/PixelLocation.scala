package observatory.visualization

import observatory.Location

case class PixelLocation(x: Int, y: Int) {
  import Location.{max_lat, max_lon}

  def toLocation(w: Int, h: Int): Location = {
    val dx = 2 * max_lon / w
    val dy = 2 * max_lat / h
    val lat = max_lat - dy * y - dy / 2
    val lon = dx * x + dx / 2 - max_lon
    Location(lat, lon)
  }
}

object PixelLocation {
  /*
  def fromLocation(w: Int, h: Int, location: Location): PixelLocation = {
    import location.{lat, lon}
    import Location.{max_lat, max_lon}
    val xD = w * (lon - max_lon) / (2 * max_lon)
    val yD = h * (max_lat - lat) / (2 * max_lat)
    val x = math.round(xD).toInt
    val y = math.round(yD).toInt
    return PixelLocation(x, y)
  }
   */
  /*
    def main(args: Array[String]): Unit = {
      def given(rnd: Random, w: Int, h: Int): (Int, Int) = (rnd.nextInt(w), rnd.nextInt(h))

      val rnd = Random
      val times = 10
      val (w, h) = 6 -> 4
      for (i <- 1 to times) {
        val (x, y) = given(rnd, w, h)
        val loc = PixelLocation(x, y).toLocation(w, h)

        val xShould = x <= w / 2 && loc.lon <= 0 || loc.lon > 0
        val yShould = y <= h / 2 && loc.lat >= 0 || loc.lat < 0
        val msg = s"$x $y $loc"
        assert(xShould, msg)
        assert(yShould, msg)
        println(msg)
      }
    }*/
}
