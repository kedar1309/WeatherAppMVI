package com.example.weatherapplication.presentation.weather

sealed interface WeatherIntent {
    data class SearchCity(val city: String) : WeatherIntent
    object LoadLastCity : WeatherIntent
    object LoadCurrentLocation : WeatherIntent
}