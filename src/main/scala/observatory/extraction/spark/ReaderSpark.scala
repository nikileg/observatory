package observatory.extraction.spark

import org.apache.spark.sql.types.StructType
import org.apache.spark.sql.{DataFrame, SparkSession}

class ReaderSpark(spark: SparkSession) {

  def readCsv(resourceName: String, schema: StructType): DataFrame = {
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
