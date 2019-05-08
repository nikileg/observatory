package observatory.extraction.spark

import java.sql.Date

import observatory.{Location, Temperature, Year}
import observatory.extraction.generators.{StationGen, TemperatureGen}
import org.scalatest.{FunSuite, Matchers}
import org.scalacheck.{Gen, Prop}
import org.scalatest.prop.Checkers

trait ExtractionSparkTest extends FunSuite with Checkers with Matchers
  with StationGen
  with TemperatureGen {

  import Gen._
  import Prop._
  import SparkTestWiring.spark._

  test("testLocationYearlyAverageRecords") {
    forAll(
      nonEmptyListOf(stationsGen) :| "stations",
      nonEmptyListOf(temperatureGen) :| "temperatures",
      choose[Year](1000, 3000) :| "year") { // todo: come up with sth smarter for a year
      (rawStations, rawTemperatures, year) =>

        val stations = rawStations.view
          .filter(_.longitude.nonEmpty)
          .filter(_.latitude.nonEmpty)

        val temperatures = rawTemperatures.view
          .filter(_.day.nonEmpty)
          .filter(_.month.nonEmpty)
          .filter(_.fahrenheit.nonEmpty)

        val expected = for {
          st <- stations
          temp <- temperatures
          if st.stnId == temp.stnId &&
            st.wbanId == temp.wbanId

          latitude <- st.latitude
          longitude <- st.longitude
          day <- temp.day
          month <- temp.month
          celsius <- temp.fahrenheit.map(Temperature.fromFahrenheit)
        } yield (new Date(year, month, day), Location(latitude, longitude), celsius)

//        val actual = SparkTestWiring.extractionService.locateTemperatures(year,)
        ???
    }
  }

  test("testLocateTemperatures") {

  }


}
