package observatory.extraction

import java.time.LocalDate

import monix.execution.Scheduler
import monix.reactive.Observable
import observatory.{Location, Temperature, Year}

import scala.io.Source

class ExtractionMonix {

  def locateTemperatures(
                          year: Year,
                          stationsFile: String,
                          temperaturesFile: String
                        ): Observable[(LocalDate, Location, Temperature)] = {
    val stations = readFile(stationsFile, "monix-io-stations")
      .map(parseStation)
    val readTemperatures = readFile(temperaturesFile, "monix-io-temperatures")
      .map(parseTemperature)
    ???
  }

  def locationYearlyAverageRecords(records: Observable[(LocalDate, Location, Temperature)]):
  Observable[(Location, Temperature)] = {
    ???
  }

  def readFile(file: String, ioSchedulerName: String): Observable[String] = {
    val ioScheduler = Scheduler.io(ioSchedulerName)
    Observable.defer {
      val inStream = getClass.getResourceAsStream(file)
      val lines = Source.fromInputStream(inStream).getLines()
      Observable.fromIterator(lines)
    }.executeOn(ioScheduler)
  }

  //  def parse[T](lines: Observable[String])(parseFun: String => T):Observable[T] = {
  //    lines.map(parseFun)
  //  }

  def parseStation(str: String): (Option[Long], Option[Long], Option[Location]) = {
    ???
  }

  def parseTemperature(str: String): Option[Double] = {
    ???
  }


  // TODO: redo this part as monix.reactive.Observable and clean up
  //
  //  def locationYearlyAverageRecords(records: Iterable[(LocalDate, Location, Temperature)]): Iterable[(Location, Temperature)] = {
  //    records
  //      .groupBy { case (_, location, _) => location }
  //      .map {
  //        case (location, data) =>
  //          val (count, sum) = data.view
  //            .map { case (_, _, temp) => (1L, temp) }
  //            .reduce[(Long, Temperature)] {
  //            case ((count1, temp1), (count2, temp2)) => (count1 + count2, temp1 + temp2)
  //          }
  //          (location, sum / count)
  //      }
  //  }
}
