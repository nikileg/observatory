package observatory.visualization.image

import com.sksamuel.scrimage.{Image, Pixel}
import observatory.{Color, Location, Temperature}
import org.apache.spark.sql.{Dataset, SparkSession}

class ImagePrinter(spark: SparkSession) {
  def createImage(w: Int, h: Int)
                 (temperatures: Dataset[(Location, Temperature)],
                  palette: Seq[(Temperature, Color)]): Image = {
    val pixels =
      (0 to w * h)
        .map(xy => (xy / h, xy / w)) // w * h => (x, y)
        .map { case (x, y) => PixelLocation(x.toInt, y.toInt) }
        .map(_.toLocation(w, h))


    ???
  }
}
