package observatory

import observatory.extraction.spark.ReaderSpark
import org.apache.spark.sql.SparkSession

object Main extends App {
  lazy val spark = SparkSession
    .builder()
    .master("local[*]")
    .appName("Coursera scala capstone project - Observatory")
    .getOrCreate()

  val reader = new ReaderSpark(spark)
  val year: Year = 1975
  val stations = reader.readCsv("target/scala-2.11/classes/stations.csv", extraction.models.stationSchema)
  val temperatures = reader.readCsv(s"target/scala-2.11/classes/$year.csv", extraction.models.temperatureSchema)
  val joined = stations.join(temperatures,
    stations("stn_id") <=> temperatures("stn_id")
      && stations("wban_id") <=> temperatures("wban_id")
  ).drop("stn_id", "wban_id")
  val joinedCount: Long = joined.count()
  println(joinedCount)
  joined.show(5)
  val filtered = joined
    .filter(joined("latitude") isNull)
//    .filter(joined("longitude") isNotNull)
  val filteredCount = filtered.count()
  filtered.show(10)
//  println(s"joinedCount=$joinedCount filteredCount=$filteredCount")
//  println(dataframe.count())
}
