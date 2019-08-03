package observatory.visualization.interpolation.color

import observatory.visualization.interpolation.InterpolationService
import observatory.{Color, Temperature}

import scala.collection.immutable.TreeMap

class ColorInterpolationService(linearInterpolationService: InterpolationService)
                               (points: Iterable[(Temperature, Color)]) {
  import linearInterpolationService.linearFract

  def interpolateColor(temperature: Temperature): Color = {
    val floor = treeMap.to(temperature).last
    val ceil = treeMap.to(temperature).head
    linearInterpolation(floor, ceil, temperature)
  }

  private[interpolation] def linearInterpolation(point1: (Temperature, Color),
                                                 point2: (Temperature, Color),
                                                 coordinate: Temperature): Color = {
    val newRed = linearFract[Temperature](
      (point1._1, point1._2.red),
      (point2._1, point2._2.red),
      coordinate) toInt

    val newGreen = linearFract[Temperature](
      (point1._1, point1._2.green),
      (point2._1, point2._2.green),
      coordinate) toInt

    val newBlue = linearFract[Temperature](
      (point1._1, point1._2.blue),
      (point2._1, point2._2.blue),
      coordinate) toInt

    Color(newRed, newGreen, newBlue)
  }

  private val treeMap: TreeMap[Temperature, Color] = TreeMap(points.toSeq: _*)
}
