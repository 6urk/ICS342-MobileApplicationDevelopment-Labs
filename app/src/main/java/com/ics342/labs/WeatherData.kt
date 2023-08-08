package com.ics342.labs

import com.squareup.moshi.Json

data class Temperature(
    @Json(name = "temp") val high: Double,

    )

data class WeatherCondition(
    val id: Int,
    val main: String,
    val description: String,
    val icon: String
)

data class WeatherData(
    @Json(name = "weather") val weatherConditions: List<WeatherCondition>,
    @Json(name = "main") private val temperature: Temperature
) {
    val highTemp: Double
        get() = temperature.high
    val iconUrl: String
        get() = "https://openweathermap.org/img/wn/${weatherConditions.firstOrNull()?.icon}@2x.png"
}
fun kelvinToFahrenheit(kelvin: Double): Double {
    return (9.0/5.0 * (kelvin - 273.15)) + 32
}

data class ForecastItem(
    @Json(name = "weather") val weatherConditions: List<WeatherCondition>
) {
    val iconUrl: String
        get() = "https://openweathermap.org/img/wn/${weatherConditions.firstOrNull()?.icon}@2x.png"
}

data class Forecast(
    @Json(name = "list") val forecasts: List<ForecastItem>
)
