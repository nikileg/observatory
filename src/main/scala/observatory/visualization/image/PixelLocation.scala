package observatory.visualization.image

import com.sksamuel.scrimage.{Image, Pixel}
import observatory.Location

case class PixelLocation(x: Int, y: Int)

object PixelLocation {
  def fromLocation(w: Int, h: Int, location: Location): PixelLocation = {
    import location.{lat, lon}
    import Location.{max_lat, max_lon}
    val xD = w * (lon - max_lon) / (2 * max_lon)
    val yD = h * (max_lat - lat) / (2 * max_lat)
    val x = math.round(xD).toInt
    val y = math.round(yD).toInt
    return PixelLocation(x, y)
  }
}
