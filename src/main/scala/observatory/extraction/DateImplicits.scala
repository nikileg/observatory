package observatory.extraction

import java.sql.Date
import java.time.LocalDate

object DateImplicits {
  @inline implicit def date2local(date: Date): LocalDate = date.toLocalDate

  @inline implicit def local2date(localDate: LocalDate): Date = Date.valueOf(localDate)
}
