package observatory.visualization.interpolation.color

import observatory.{Color, Temperature}
import observatory.visualization.interpolation.Interpolation

import scala.collection.immutable.TreeMap

class ColorInterpolationService(palette: Iterable[(Temperature, Color)]) {
  private val interpolateTemperature = Interpolation.fractionalInterpolation[Temperature].linear _

  def interpolateColor(temperature: Temperature): Color = {
    val floor = treeMap.to(temperature).last
    val ceil = treeMap.to(temperature).head
    linearInterpolation(floor, ceil, temperature)
  }

  private[interpolation] def linearInterpolation(point1: (Temperature, Color),
                                                 point2: (Temperature, Color),
                                                 coordinate: Temperature): Color = {
    val (color1, color2) = (point1._2, point2._2)

    def newComponent(getter: Color => Int): Int = {
      val getComponents: (Color => Int) => ((Color, Color)) => (Int, Int) = cToInt => {
        case (c1, c2) => (cToInt(c1), cToInt(c2))
      }
      val interpolateComponents: ((Int, Int)) => Int = {
        case (rgb1, rgb2) => interpolateTemperature(
          (point1._1, rgb1),
          (point2._1, rgb2),
          coordinate
        ).toInt
      }
      getComponents(getter) andThen interpolateComponents apply(color1, color2)
    }

    val newRed = newComponent(_.red)
    val newGreen = newComponent(_.green)
    val newBlue = newComponent(_.blue)
    Color(newRed, newGreen, newBlue)
  }

  private val treeMap: TreeMap[Temperature, Color] = TreeMap(palette.toSeq: _*)
}
