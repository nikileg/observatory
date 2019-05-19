package observatory.visualization.interpolation

import observatory.{Color, Temperature}

class ColorInterpolationService(linearInterpolationService: LinearInterpolationService) {

  import linearInterpolationService.singleArgument

  def linearInterpolation(point1: (Temperature, Color),
                          point2: (Temperature, Color),
                          coordinate: Temperature): Color = {
    val newRed = singleArgument[Temperature](
      (point1._1, point1._2.red),
      (point2._1, point2._2.red),
      coordinate) toInt

    val newGreen = singleArgument[Temperature](
      (point1._1, point1._2.green),
      (point2._1, point2._2.green),
      coordinate) toInt

    val newBlue = singleArgument[Temperature](
      (point1._1, point1._2.blue),
      (point2._1, point2._2.blue),
      coordinate) toInt

    Color(newRed, newGreen, newBlue)
  }
}
