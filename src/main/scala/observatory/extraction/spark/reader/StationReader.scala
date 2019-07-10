package observatory.extraction.spark.reader

import observatory.extraction.spark.csvmodel.StationCsv
import org.apache.spark.sql.{Dataset, SparkSession}
import org.apache.spark.sql.types.{DoubleType, StringType, StructField, StructType}

class StationReader(sparkSession: SparkSession) extends ReaderSpark {
  override protected val spark: SparkSession = sparkSession

  def readStations(resourceName: String): Dataset[StationCsv] = {
    import spark.implicits._

    readCsv(resourceName, StationReader.schema)
      .filter('latitude isNotNull)
      .filter('longitude isNotNull)
      .as[StationCsv]
  }
}

object StationReader {
  val schema = StructType(
    Array(
      StructField(name = "stnId", dataType = StringType, nullable = true),
      StructField(name = "wbanId", dataType = StringType, nullable = true),
      StructField(name = "latitude", dataType = DoubleType, nullable = false),
      StructField(name = "longitude", dataType = DoubleType, nullable = false)
    )
  )
}
