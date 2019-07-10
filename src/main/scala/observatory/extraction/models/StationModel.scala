package observatory.extraction.models

import observatory.Location
import observatory.extraction.spark.csvmodel.StationCsv

case class StationModel(stn: Option[String],
                        wban: Option[String],
                        location: Location)

object StationModel {
  def fromCsvRow(csvRow: StationCsv): StationModel = {
    val StationCsv(stn, wban, lat, lon) = csvRow
    StationModel(stn, wban, Location(lat, lon))
  }
}
