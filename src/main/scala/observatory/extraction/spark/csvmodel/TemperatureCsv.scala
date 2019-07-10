package observatory.extraction.spark.csvmodel

case class TemperatureCsv(stnId: Option[String],
                          wbanId: Option[String],
                          month: Int,
                          day: Int,
                          fahrenheit: Double)
