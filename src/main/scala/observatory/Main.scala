package observatory

import observatory.extraction.spark.dao.{StationsDao, TemperaturesDao}
import observatory.extraction.spark.{ExtractionSpark, ReaderSpark}
import org.apache.spark.sql.SparkSession

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

  val stationsDao = new StationsDao(reader, spark)
  val temperaturesDao = new TemperaturesDao(reader, spark)

  val service = new ExtractionSpark(spark, stationsDao, temperaturesDao)

  val extracted = service
    .locateTemperatures(year, stationsFile, temperaturesFile)

  service
    .locationYearlyAverageRecords(extracted)
    .show()
}
