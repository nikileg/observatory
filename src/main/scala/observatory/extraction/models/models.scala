package observatory.extraction.models

import java.time.LocalDate

import observatory.{Location, Temperature}

// csv representation

case class StationCsv(stnId: Option[STN],
                      wbanId: Option[WBAN],
                      latitude: Option[Latitude],
                      longitude: Option[Longitude])

case class TemperatureCsv(stnId: Option[STN],
                          wbanId: Option[WBAN],
                          month: Option[Month],
                          day: Option[Day],
                          fahrenheit: Option[Fahrenheit])

// as relational model (?)

case class StationModel(stationId: (STN, WBAN),
                        location: Location)

case class TemperatureModel(stationId: (STN, WBAN),
                            date: LocalDate,
                            temperature: Temperature)

