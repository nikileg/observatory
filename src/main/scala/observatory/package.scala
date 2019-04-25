package object observatory {
  type Temperature = Double // °C, introduced in Week 1
  type Year = Int // Calendar year, introduced in Week 1

  object Temperature {
    def fromFahrenheit(fahrenheits: Double): Temperature = {
      (fahrenheits - 32.0) * 5 / 9
    }
  }
}
