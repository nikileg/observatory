package observatory.extraction

import java.time.LocalDate

import observatory.{Location, Temperature}
import org.apache.spark.sql.types.{BooleanType, DoubleType, IntegerType, StringType, StructField, StructType}

package object models {
  type STN = Long
  type WBAN = Long
  type Latitude = Double
  type Longitude = Double

  type Month = Int
  type Day = Int
  type Fahrenheit = Double

  val stationSchema = StructType(
    Array(
      StructField(name = "stn_id", dataType = StringType, nullable = true),
      StructField(name = "wban_id", dataType = StringType, nullable = true),
      StructField(name = "latitude", dataType = DoubleType, nullable = false),
      StructField(name = "longitude", dataType = DoubleType, nullable = false)
    )
  )

  val temperatureSchema = StructType(
    Array(
      StructField(name = "stn_id", dataType = StringType, nullable = true),
      StructField(name = "wban_id", dataType = StringType, nullable = true),
      StructField(name = "month", dataType = IntegerType, nullable = false),
      StructField(name = "day", dataType = IntegerType, nullable = false),
      StructField(name = "fahrenheit", dataType = DoubleType, nullable = false)
    )
  )
}
