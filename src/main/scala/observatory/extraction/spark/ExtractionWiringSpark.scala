package observatory.extraction.spark

import observatory.extraction.spark.dao.{StationsDao, TemperaturesDao}
import org.apache.spark.sql.SparkSession
import org.apache.log4j.{Level, Logger}

object ExtractionWiringSpark {
  Logger.getLogger("org.apache.spark").setLevel(Level.WARN)

  lazy val spark = SparkSession
    .builder()
    .master("local[*]")
    .appName("Coursera scala capstone project - Observatory")
    .getOrCreate()

  lazy val reader = new ReaderSpark(spark)

  lazy val stationsDao = new StationsDao(reader, spark)
  lazy val temperaturesDao = new TemperaturesDao(reader, spark)

  lazy val extractionService = new ExtractionSpark(spark, stationsDao, temperaturesDao)
}
