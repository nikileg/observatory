package observatory.extraction.generators

import observatory.extraction.models.{Latitude, Longitude, STN, StationCsv, WBAN}
import org.scalacheck.{Arbitrary, Gen}

trait StationGen {

  import Gen._

  lazy val stationsGen = for {
    stn <- option(posNum[STN])
    wban <- option(posNum[WBAN])
    latitude <- option(choose[Latitude](-maxLatitude, maxLatitude))
    longitude <- option(choose[Longitude](-maxLongitude, maxLongitude))
  } yield StationCsv(stn, wban, latitude, longitude)

  implicit lazy val arbStation = Arbitrary(stationsGen)

  private final val maxLatitude = 90.0
  private final val maxLongitude = 180.0
}
