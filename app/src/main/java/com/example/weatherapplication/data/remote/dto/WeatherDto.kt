package com.example.weatherapplication.data.remote.dto

data class WeatherDto(
    val name: String,
    val main: MainDto,
    val weather: List<WeatherInfoDto>
)

data class MainDto(
    val temp: Double,
    val humidity: Int
)

data class WeatherInfoDto(
    val description: String,
    val icon: String
)