package observatory.extraction.spark

import org.apache.spark.sql.{Dataset, SparkSession}
import org.apache.spark.sql.types.{DoubleType, StringType, StructField, StructType}

class StationsDao(reader: ReaderSpark, spark: SparkSession) {

  import spark.implicits._

  def get(resourceName: String): Dataset[(String, String, Double, Double)] = {
    reader
      .readCsv(resourceName, stationSchema)
      .as[(String, String, Double, Double)]
      .filter($"longitude" isNotNull)
      .filter($"latitude" isNotNull)
  }

  private val stationSchema = StructType(
    Array(
      StructField(name = "stn_id", dataType = StringType, nullable = true),
      StructField(name = "wban_id", dataType = StringType, nullable = true),
      StructField(name = "latitude", dataType = DoubleType, nullable = false),
      StructField(name = "longitude", dataType = DoubleType, nullable = false)
    )
  )
}


