package observatory.extraction.spark.reader

import observatory.extraction.spark.csvmodel.TemperatureCsv
import org.apache.spark.sql.types._
import org.apache.spark.sql.{Dataset, SparkSession}

class TemperatureReader(sparkSession: SparkSession) extends ReaderSpark {
  override protected val spark: SparkSession = sparkSession

  def readTemperatures(resourceName: String): Dataset[TemperatureCsv] = {
    import spark.implicits._

    readCsv(resourceName, TemperatureReader.schema)
      .filter('month isNotNull)
      .filter('day isNotNull)
      .filter('fahrenheit isNotNull)
      .as[TemperatureCsv]
  }
}

object TemperatureReader {
  val schema = StructType(
    Array(
      StructField(name = "stnId", dataType = LongType, nullable = true),
      StructField(name = "wbanId", dataType = LongType, nullable = true),
      StructField(name = "month", dataType = IntegerType, nullable = false),
      StructField(name = "day", dataType = IntegerType, nullable = false),
      StructField(name = "fahrenheit", dataType = DoubleType, nullable = false)
    )
  )
}
