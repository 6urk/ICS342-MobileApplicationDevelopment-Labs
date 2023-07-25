package com.ics342.labs

import com.squareup.moshi.Json

data class Temperature(
    val high: Int
)

data class WeatherCondition(
    val icon: String
)

data class WeatherData(
    @Json(name = "main") val temperature: Temperature,
    @Json(name = "weather") val weatherConditions: List<WeatherCondition>
) {
    val highTemp: Int
        get() = temperature.high

    val iconUrl: String
        get() = "https://openweathermap.org/img/wn/${weatherConditions.firstOrNull()?.icon}@2x.png"
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