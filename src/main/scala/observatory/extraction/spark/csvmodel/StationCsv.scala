package observatory.extraction.spark.csvmodel

case class StationCsv(stnId: Option[String],
                      wbanId: Option[String],
                      latitude: Double,
                      longitude: Double)
