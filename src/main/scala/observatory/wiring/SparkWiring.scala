package observatory.wiring

import observatory.extraction.spark.dao.{StationsDao, TemperaturesDao}
import observatory.extraction.spark.ExtractionSpark
import observatory.extraction.spark.reader.{ReaderSpark, StationReader, TemperatureReader}
import org.apache.spark.sql.SparkSession
import org.apache.log4j.{Level, Logger}

object SparkWiring {
  Logger.getLogger("org.apache.spark").setLevel(Level.WARN)

  lazy val spark = SparkSession
    .builder()
    .master("local[*]")
    .appName("Coursera scala capstone project - Observatory")
    .getOrCreate()

  lazy val stationsReader = new StationReader(spark)
  lazy val temperatureReader = new TemperatureReader(spark)

  lazy val stationsDao = new StationsDao(spark)
  lazy val temperaturesDao = new TemperaturesDao(spark)

  lazy val extractionSpark = new ExtractionSpark(
    spark,
    stationsDao,
    stationsReader,
    temperaturesDao,
    temperatureReader
  )
}
