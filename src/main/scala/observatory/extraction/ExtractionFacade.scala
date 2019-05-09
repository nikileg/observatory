package observatory.extraction

import java.sql.Date
import java.time.LocalDate

import observatory.extraction.spark.{ExtractionWiringSpark, DateImplicits}
import observatory.{Location, Temperature, Year}

object ExtractionFacade {

  import ExtractionWiringSpark._
  import ExtractionWiringSpark.spark.implicits._
  import DateImplicits._

  /**
    * @param year             Year number
    * @param stationsFile     Path of the stations resource file to use (e.g. "/stations.csv")
    * @param temperaturesFile Path of the temperatures resource file to use (e.g. "/1975.csv")
    * @return A sequence containing triplets (date, location, temperature)
    */
  def locateTemperatures(year: Year, stationsFile: String, temperaturesFile: String): Iterable[(LocalDate, Location, Temperature)] = {
    extractionService
      .locateTemperatures(year, stationsFile, temperaturesFile)
      .rdd
      .map { case (date, loc, temp) => (date: LocalDate, loc, temp) }
      .collect()
  }

  /**
    * @param records A sequence containing triplets (date, location, temperature)
    * @return A sequence containing, for each location, the average temperature over the year.
    */
  def locationYearlyAverageRecords(records: Iterable[(LocalDate, Location, Temperature)]): Iterable[(Location, Temperature)] = {

    val ds = records
      .toStream
      .toDS()
      .map { case (locDate, loc, temp) => (locDate: Date, loc, temp) }

    ExtractionWiringSpark
      .extractionService
      .locationYearlyAverageRecords(ds)
      .collect()
  }
}