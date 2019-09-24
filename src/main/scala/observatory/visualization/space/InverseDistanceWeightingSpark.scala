package observatory.visualization.space

import observatory.{Location, Temperature}
import org.apache.spark.sql.{Dataset, SparkSession}

object InverseDistanceWeightingSpark {

  def predictTemperature(spark: SparkSession, distance: (Location, Location) => BigDecimal, p: Int)
                        (temperatures: Dataset[(Temperature, Location)], location: Location): Temperature = {
    import spark.implicits._
    val distances = temperatures
      .map { case (ui, xi) => (ui, distance(location, xi)) }
    val condition = distances
      .filter(_._2 == 0)
      .take(1)

    if (condition.nonEmpty) condition(0)._1

    val ws = distances.map { case (ui, xi) => (ui, xi.pow(-p)) }
    val num = distances
      .joinWith(ws, distances("ui") === ws("ui"))
      .map { case ((dUi, xi), (wsUi, wi)) => (dUi, xi * wi) }

    val res = num.reduce(sumFun)._2 / distances.reduce(sumFun)._2
    res.doubleValue()
  }

  private val sumFun: ((Temperature, BigDecimal), (Temperature, BigDecimal)) => (Temperature, BigDecimal) = {
    case ((ui: Temperature, xw1: BigDecimal), (_, xw2: BigDecimal)) => (ui, xw1 + xw2)
  }
}
