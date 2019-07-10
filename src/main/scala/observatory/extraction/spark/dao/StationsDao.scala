package observatory.extraction.spark.dao

import observatory.extraction.models.StationModel
import observatory.extraction.spark.csvmodel.StationCsv
import org.apache.spark.sql.{Dataset, SparkSession}

class StationsDao(spark: SparkSession) {
  import spark.implicits._

  //TODO: rename
  def get(parsed: Dataset[StationCsv]): Dataset[StationModel] = {
    parsed
      .map(StationModel.fromCsvRow)
  }
}
