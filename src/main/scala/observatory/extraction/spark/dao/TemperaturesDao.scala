package observatory.extraction.spark.dao

import observatory.Year
import observatory.extraction.spark.ReaderSpark
import org.apache.spark.sql.types._
import org.apache.spark.sql.{Dataset, SparkSession}

class TemperaturesDao(reader: ReaderSpark, spark: SparkSession) {

  import spark.implicits._

  def getByYear(year: Year, resourceName: String): Dataset[(String, String, Int, Int, Double)] =
    reader
    .readCsv(resourceName, temperatureSchema)
    .as[(String, String, Int, Int, Double)]
    .filter($"month" isNotNull)
    .filter($"day" isNotNull)
    .filter($"fahrenheit" isNotNull)
    .filter($"fahrenheit" =!= invalidFahrenheit)

  private val invalidFahrenheit: Double = 9999.9

  private val temperatureSchema = StructType(
    Array(
      StructField(name = "stn_id", dataType = StringType, nullable = true),
      StructField(name = "wban_id", dataType = StringType, nullable = true),
      StructField(name = "month", dataType = IntegerType, nullable = false),
      StructField(name = "day", dataType = IntegerType, nullable = false),
      StructField(name = "fahrenheit", dataType = DoubleType, nullable = false)
    )
  )
}
