package com.example.weatherapplication.presentation.weather

sealed interface WeatherEffect {
    object RequestLocationPermission : WeatherEffect
}