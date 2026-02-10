package com.example.weatherapplication.domain

import com.example.weatherapplication.data.remote.dto.WeatherDto

data class Weather(
    val city: String,
    val temperature: Double,
    val humidity: Int,
    val description: String,
    val icon: String
)

fun WeatherDto.toDomain(): Weather {
    return Weather(
        city = name,
        temperature = main.temp,
        humidity = main.humidity,
        description = weather.first().description,
        icon = weather.first().icon
    )
}