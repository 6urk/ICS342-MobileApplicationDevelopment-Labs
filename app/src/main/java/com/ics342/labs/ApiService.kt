package com.ics342.labs

import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("weather")
    suspend fun getCurrentWeather(
        @Query("zip") zipcode: String,
        @Query("appid") apiKey: String
    ): WeatherData

    @GET("forecast")
    suspend fun getForecast(
        @Query("zip") zipcode: String,
        @Query("appid") apiKey: String
    ): Forecast
}