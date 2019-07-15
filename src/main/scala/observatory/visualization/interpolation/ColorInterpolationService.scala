package observatory.visualization.interpolation

import observatory.{Color, Temperature}

class ColorInterpolationService(linearInterpolationService: InterpolationService) {

  import linearInterpolationService.linearFract

  def linearInterpolation(point1: (Temperature, Color),
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
}
