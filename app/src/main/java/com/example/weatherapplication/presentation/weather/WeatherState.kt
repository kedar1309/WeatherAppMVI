package com.example.weatherapplication.presentation.weather

import com.example.weatherapplication.domain.Weather

sealed interface WeatherState {
    object Idle : WeatherState
    object Loading : WeatherState
    data class Success(val weather: Weather) : WeatherState
    data class Error(val message: String) : WeatherState
}