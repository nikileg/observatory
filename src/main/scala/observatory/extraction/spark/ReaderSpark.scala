package observatory.extraction.spark

import observatory.extraction.models
import org.apache.spark.sql.types.StructType
import org.apache.spark.sql.{DataFrame, SparkSession}

class ReaderSpark(spark: SparkSession) {

  def readCsv(file: String, schema: StructType): DataFrame = {
    spark
      .read
      .schema(schema)
      .option("separator", ",")
      .option("inferSchema", false)
      .csv(file)
  }

}
