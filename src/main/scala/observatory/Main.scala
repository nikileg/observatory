package observatory

import java.time.LocalDate

import observatory.extraction.ExtractionSpark
import observatory.extraction.spark.{ReaderSpark, StationsDao, TemperaturesDao}
import org.apache.spark.sql.{Dataset, SparkSession}

object Main extends App {
  lazy val spark = SparkSession
    .builder()
    .master("local[*]")
    .appName("Coursera scala capstone project - Observatory")
    .getOrCreate()

  val reader = new ReaderSpark(spark)
  val year: Year = 1975

  val stationsFile = "/stations.csv"
  val temperaturesFile = s"/$year.csv"

  //  val stations = reader.readCsv(stationsFile, extraction.models.stationSchema)
  //  val temperatures = reader.readCsv(temperaturesFile, extraction.models.temperatureSchema)

  val stationsDao = new StationsDao(reader, spark)
  val temperaturesDao = new TemperaturesDao(reader, spark)

  val service = new ExtractionSpark(spark, stationsDao, temperaturesDao)

  service
    .locateTemperatures(year, stationsFile, temperaturesFile)
    .show()

}
