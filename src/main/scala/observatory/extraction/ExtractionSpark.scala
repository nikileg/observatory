package observatory.extraction

import java.sql.Date
import java.time.LocalDate

import observatory.extraction.spark.{StationsDao, TemperaturesDao}
import observatory.{Location, Temperature, Year}
import org.apache.spark.sql.{Dataset, SparkSession}

class ExtractionSpark(spark: SparkSession,
                      stationsDao: StationsDao,
                      temperaturesDao: TemperaturesDao) {

  import spark.implicits._

  def locateTemperatures(year: Year,
                         stationsFile: String,
                         temperaturesFile: String): Dataset[(Date, Location, Temperature)] = {
    val stations = stationsDao.get(stationsFile)
    val temperatures = temperaturesDao.getByYear(year, temperaturesFile)

    val joined = stations.join(temperatures,
      stations("stn_id") <=> temperatures("stn_id")
        && stations("wban_id") <=> temperatures("wban_id")
    )
    val result = joined
      .select($"day", $"month", $"latitude", $"longitude", $"fahrenheit")
      .as[(Int, Int, Double, Double, Double)]
      .map { case (day, month, lat, lon, fahr) =>
        (new Date(year, month, day), Location(lat, lon), Temperature.fromFahrenheit(fahr))
      }
    result
  }

  def locationYearlyAverageRecords(records: Dataset[(Date, Location, Temperature)]): Dataset[(Location, Temperature)] = {
    records
    ???
  }
}
