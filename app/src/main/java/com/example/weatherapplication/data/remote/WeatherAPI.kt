package com.example.weatherapplication.data.remote

import com.example.weatherapplication.data.remote.dto.GeoDto
import com.example.weatherapplication.data.remote.dto.WeatherDto
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherAPI {

    @GET("geo/1.0/direct")
    suspend fun getCoordinates(
        @Query("q") city: String,
        @Query("limit") limit: Int = 1,
        @Query("appid") apiKey: String
    ): List<GeoDto>

    @GET("data/2.5/weather")
    suspend fun getWeather(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") apiKey: String,
        @Query("units") units: String = "metric"
    ): WeatherDto
}