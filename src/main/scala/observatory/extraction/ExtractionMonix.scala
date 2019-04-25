package observatory.extraction

import java.time.LocalDate

import observatory.{Location, Temperature, Year}

class ExtractionMonix {

  def locateTemperatures(
                          year: Year,
                          stationsFile: String,
                          temperaturesFile: String
                        ): Iterable[(LocalDate, Location, Temperature)] = {
    ???
  }

  def locationYearlyAverageRecords(records: Iterable[(LocalDate, Location, Temperature)]): Iterable[(Location, Temperature)] = {
    records
      .groupBy { case (_, location, _) => location }
      .map {
        case (location, data) =>
          val (count, sum) = data.view
            .map { case (_, _, temp) => (1L, temp) }
            .reduce[(Long, Temperature)] {
            case ((count1, temp1), (count2, temp2)) => (count1 + count2, temp1 + temp2)
          }
          (location, sum / count)
      }
  }
}
