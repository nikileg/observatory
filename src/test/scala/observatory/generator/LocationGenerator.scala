package observatory.generator

import observatory.Location
import org.scalacheck.{Arbitrary, Gen}

object LocationGenerator {
  def apply(): Gen[Location] = for {
    lat <- Gen.chooseNum(minLatitude, maxLatitude)
    lon <- Gen.chooseNum(minLongitude, maxLongitude)
  } yield Location(lat, lon)

  private val minLatitude = -90
  private val maxLatitude = 90
  private val minLongitude = -180
  private val maxLongitude = 180

  implicit val arbLocation = Arbitrary[Location](apply())
}
