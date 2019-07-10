package observatory.extraction.spark.dao

import observatory.Year
import observatory.extraction.models.TemperatureModel
import observatory.extraction.spark.csvmodel.TemperatureCsv
import org.apache.spark.sql.{Dataset, SparkSession}

class TemperaturesDao(spark: SparkSession) {
  import spark.implicits._

  //TODO: rename with more appropriate title
  def getByYear(parsed: Dataset[TemperatureCsv],
                year: Year): Dataset[TemperatureModel] = {
    parsed
      .filter('fahrenheit < invalidFahrenheit)
      .map(TemperatureModel.fromCsvRow(_, year))
  }

  private[dao] val invalidFahrenheit: Double = 9999.9
}
