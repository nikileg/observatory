package observatory.visualization.image

import com.sksamuel.scrimage.{Image, Pixel}
import observatory.visualization.interpolation.color.ColorInterpolationService
import observatory.visualization.interpolation.space.SpacialInterpolationService
import observatory.{Color, Location, Temperature}
import org.apache.spark.sql.{Dataset, SparkSession}
import observatory.visualization.util

class ImagePrinter(spark: SparkSession)
                  (colorService: ColorInterpolationService,
                   spaceService: SpacialInterpolationService) {
  def createImage(w: Int, h: Int)
                 (temperatures: Dataset[(Location, Temperature)],
                  palette: Seq[(Temperature, Color)]): Image = {
    val pixels =
      (0 to w * h)
        .map(xy => (xy / h, xy / w)) // w * h => (x, y)
        .map { case (x, y) => PixelLocation(x.toInt, y.toInt) }
        .map(_.toLocation(w, h))
        .map(loc => approximateTemperature(loc)(temperatures))
          .map(t => colorService.interpolateColor(t) )

    ???
  }

  def approximateTemperature(location: Location)
                            (temperatures: Dataset[(Location, Temperature)]): Temperature = {
    import util._
    val defaultTemperature = 0

    //    temperatures.rdd
    //      .closestBy(()
    ???
  }

  implicit object LocationIsNumeric extends Numeric[Location] {

    override def plus(x: Location, y: Location): Location = Location(x.lat + y.lat, x.lon + y.lon)

    override def minus(x: Location, y: Location): Location = Location(x.lat - y.lat, x.lon - y.lon)

    override def times(x: Location, y: Location): Location = ???

    override def negate(x: Location): Location = ???

    override def fromInt(x: Int): Location = Location(x, x)

    override def toInt(x: Location): Int = toDouble(x).toInt

    override def toLong(x: Location): Long = toDouble(x).toLong

    override def toFloat(x: Location): Float = toDouble(x).toFloat

    override def toDouble(x: Location): Double = math.sqrt(x.lat * x.lat + x.lon * x.lon)

    override def compare(x: Location, y: Location): Int = x.toDouble().compareTo(y.toDouble())
  }

}
