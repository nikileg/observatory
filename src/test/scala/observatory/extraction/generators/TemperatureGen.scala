package observatory.extraction.generators

import java.time.{LocalDate, Year => JYear}

import observatory.extraction.models.{Fahrenheit, STN, TemperatureCsv, WBAN}
import org.scalacheck.{Arbitrary, Gen}

trait TemperatureGen {
  import Gen._

  lazy val dateGen = for {
    year0 <- choose(JYear.MIN_VALUE, JYear.MAX_VALUE - 1)
    plusDays <- choose(0, 365)
  } yield LocalDate.of(year0, 0, 0).plusDays(plusDays)

  lazy val stationsGen = for {
    stn <- option(posNum[STN])
    wban <- option(posNum[WBAN])
    date <- option(dateGen)
    month = date.map(_.getMonthValue)
    day = date.map(_.getDayOfMonth)
    fahrenheit <- option(chooseNum[Fahrenheit](zeroKelvinAsFahrenheit, maxTemperature, maxTemperature))
  } yield TemperatureCsv(stn, wban, month, day, fahrenheit)

  implicit lazy val arbStation = Arbitrary(stationsGen)

  private final val maxTemperature = 9999.9

  private final val zeroKelvinAsFahrenheit = (0 - 273.15) * 9 / 5 + 32 // ~= -459.7*F
}
