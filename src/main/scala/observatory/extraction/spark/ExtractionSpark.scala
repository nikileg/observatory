package observatory.extraction.spark

import java.sql.Date

import observatory.extraction.spark.dao.{StationsDao, TemperaturesDao}
import observatory.{Location, Temperature, Year}
import observatory.extraction.spark.reader.{StationReader, TemperatureReader}
import org.apache.spark.sql.{Dataset, SparkSession}

class ExtractionSpark(spark: SparkSession,
                      stationsDao: StationsDao,
                      stationReader: StationReader,
                      temperaturesDao: TemperaturesDao,
                      temperatureReader: TemperatureReader) {
  import spark.implicits._

  def locateTemperatures(year: Year,
                         stationsFile: String,
                         temperaturesFile: String): Dataset[(Date, Location, Temperature)] = {
    val stations = stationReader
      .readStations(stationsFile)
      .transform(stationsDao.get)

    val temperatures = temperatureReader
      .readTemperatures(temperaturesFile)
      .transform(temperaturesDao.getByYear(_, year))

    val result = temperatures
      .joinWith(stations,
        stations("stn") <=> temperatures("stn") &&
          stations("wban") <=> temperatures("wban")
      )
      .map {
        case (temperatureM, stationM) =>
          (temperatureM.date, stationM.location, temperatureM.temperature)
      }
    result
  }

  def locationYearlyAverageRecords(records: Dataset[(Date, Location, Temperature)]): Dataset[(Location, Temperature)] = {
    records
      .groupBy("_2")
      .avg("_3")
      .as[(Location, Temperature)]
  }
}
