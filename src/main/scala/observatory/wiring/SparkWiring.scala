package observatory.wiring

import org.apache.spark.sql.SparkSession

object SparkWiring {
  lazy val spark = SparkSession
    .builder()
    .master("local[*]")
    .appName("Coursera scala capstone project - Observatory")
    .getOrCreate()
}
