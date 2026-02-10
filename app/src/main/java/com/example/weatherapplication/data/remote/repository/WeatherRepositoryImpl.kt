package com.example.weatherapplication.data.remote.repository

import com.example.weatherapplication.data.remote.WeatherAPI
import com.example.weatherapplication.data.remote.local.LastCityStorage
import com.example.weatherapplication.domain.Weather
import com.example.weatherapplication.domain.repository.WeatherRepository
import com.example.weatherapplication.domain.toDomain
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val api: WeatherAPI,
    private val storage: LastCityStorage
) : WeatherRepository {

    private val API_KEY = "8464e632342397804902c893305c789c"

    override suspend fun getWeather(city: String): Weather {
        storage.saveCity(city)
        val geo = api.getCoordinates(city, apiKey = API_KEY).first()

        val weatherDto = api.getWeather(
            lat = geo.lat,
            lon = geo.lon,
            apiKey = API_KEY
        )

        return weatherDto.toDomain()
    }
}