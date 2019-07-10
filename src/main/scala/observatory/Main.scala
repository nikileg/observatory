package observatory

import observatory.wiring.SparkWiring
import org.apache.log4j.{Level, Logger}

object Main extends App {
  Logger.getLogger("org.apache.spark").setLevel(Level.WARN)
  val year: Year = 1975

  val stationsFile = "/stations.csv"
  val temperaturesFile = s"/$year.csv"

  import SparkWiring._

  val locatedTemperatures = extractionSpark.locateTemperatures(year, stationsFile, temperaturesFile)
  locatedTemperatures.show()

  val avgTemperatures = extractionSpark.locationYearlyAverageRecords(locatedTemperatures)
  avgTemperatures.show()
}
