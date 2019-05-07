package observatory.extraction.generators

import observatory.extraction.models.{Latitude, Longitude, STN, StationCsv, WBAN}
import org.scalacheck.{Arbitrary, Gen}

trait StationGen {

  import Gen._

  lazy val stationsGen = for {
    stn <- option(posNum[STN])
    wban <- option(posNum[WBAN])
    latitude <- option(choose[Latitude](-maxCoordinate, maxCoordinate))
    longitude <- option(choose[Longitude](-maxCoordinate, maxCoordinate))
  } yield StationCsv(stn, wban, latitude, longitude)

  implicit lazy val arbStation = Arbitrary(stationsGen)

  private final val maxCoordinate = 90.0
}
