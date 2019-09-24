package observatory.visualization.interpolation

import observatory.{Color, Temperature}

import scala.collection.immutable.TreeMap

trait ColorInterpolationService {

  def interpolateColor(temperature: Temperature): Color = {
    interpolateColor(floor(temperature), ceil(temperature), temperature)
  }

  //  ----- Abstract -----

  protected val floor: Temperature => (Temperature, Color)
  protected val ceil: Temperature => (Temperature, Color)

  //  ----- Internal -----

  private[interpolation] def interpolateColor(point1: (Temperature, Color),
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

  private val interpolateTemperature = Interpolation.fractionalInterpolation[Temperature].linear _
}

object ColorInterpolationService {
  def treeColorInterpolationService(points: Iterable[(Temperature, Color)]): ColorInterpolationService = {
    val palette = TreeMap[Temperature, Color](points.toStream: _*)
    new ColorInterpolationService {
      override protected val floor: Temperature => (Temperature, Color) = t => palette.to(t).last
      override protected val ceil: Temperature => (Temperature, Color) = t => palette.to(t).head
    }
  }
}
