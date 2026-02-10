package com.example.weatherapplication.presentation.weather

import com.example.weatherapplication.domain.Weather

sealed interface WeatherResult {
    object Loading : WeatherResult
    data class Success(val weather: Weather) : WeatherResult
    data class Error(val message: String) : WeatherResult
}