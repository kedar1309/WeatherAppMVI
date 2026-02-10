package com.example.weatherapplication.domain.usecase

import com.example.weatherapplication.domain.Weather
import com.example.weatherapplication.domain.repository.WeatherRepository
import javax.inject.Inject

class GetWeatherUseCase @Inject constructor(
    private val repository: WeatherRepository
) {
    suspend operator fun invoke(city: String): Weather {
        return repository.getWeather(city)
    }
}