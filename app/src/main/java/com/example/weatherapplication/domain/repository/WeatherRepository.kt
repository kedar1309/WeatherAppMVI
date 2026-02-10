package com.example.weatherapplication.domain.repository

import com.example.weatherapplication.domain.Weather

interface WeatherRepository {
    suspend fun getWeather(city: String): Weather
}