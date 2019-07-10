package observatory.extraction.spark.reader

import org.apache.spark.sql.types.StructType
import org.apache.spark.sql.{DataFrame, SparkSession}

trait ReaderSpark {
  protected def spark: SparkSession

  protected def readCsv(resourceName: String, schema: StructType): DataFrame = {
    val filePath = getClass.getResource(resourceName)
    spark
      .read
      .schema(schema)
      .option("sep", ",")
      .option("nullValue", "")
      .option("inferSchema", false)
      .option("mode", "DROPMALFORMED")
      .csv(filePath.toString)
  }
}
