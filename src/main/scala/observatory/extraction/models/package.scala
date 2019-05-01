package observatory.extraction

import java.time.LocalDate

import observatory.{Location, Temperature}

package object models {
  type STN = Long
  type WBAN = Long
  type Latitude = Double
  type Longitude = Double

  type Month = Int
  type Day = Int
  type Fahrenheit = Double
}
