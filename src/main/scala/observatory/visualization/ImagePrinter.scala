package observatory.visualization

import com.sksamuel.scrimage.Image
import observatory.{Location, Temperature}
import observatory.visualization.interpolation.ColorInterpolationService
import org.apache.spark.sql.{Dataset, SparkSession}

class ImagePrinter(spark: SparkSession)
                  (colorService: ColorInterpolationService) {
  def createImage(predictTemperature: (Dataset[(Temperature, Location)], Location) => Temperature)
                 (w: Int, h: Int)
                 (temperatures: Dataset[(Location, Temperature)]): Image = {
    val pixels =
      (0 to w * h)
        .map(xy => (xy / h, xy / w)) // w * h => (x, y)
        .map { case (x, y) => PixelLocation(x.toInt, y.toInt) }
        .map(_.toLocation(w, h))
        .map(loc => predictTemperature(temperatures, loc))
        .map(t => colorService.interpolateColor(t))

    ???
  }

}
