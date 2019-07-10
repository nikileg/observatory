package observatory.extraction.models

import java.sql.Date
import java.time.LocalDate

import observatory.extraction.DateImplicits
import observatory.{Temperature, Year}
import observatory.extraction.spark.csvmodel.TemperatureCsv

case class TemperatureModel(stn: Option[String],
                            wban: Option[String],
                            date: Date,
                            temperature: Temperature) {
  import DateImplicits._

  def localDate: LocalDate = date
}

object TemperatureModel {
  import DateImplicits._

  def fromCsvRow(csvRow: TemperatureCsv, year: Year): TemperatureModel = {
    val TemperatureCsv(stn, wban, month, day, fahr) = csvRow
    val temperature = Temperature.fromFahrenheit(fahr)
    TemperatureModel(stn, wban, LocalDate.of(year, month, day), temperature)
  }
}
