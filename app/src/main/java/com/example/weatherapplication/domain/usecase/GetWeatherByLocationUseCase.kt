package com.example.weatherapplication.domain.usecase

import com.example.weatherapplication.data.remote.WeatherAPI
import com.example.weatherapplication.data.remote.location.LocationProvider
import com.example.weatherapplication.domain.Weather
import com.example.weatherapplication.domain.toDomain
import javax.inject.Inject

class GetWeatherByLocationUseCase @Inject constructor(
    private val locationProvider: LocationProvider,
    private val api: WeatherAPI
) {
    private val API_KEY = "8464e632342397804902c893305c789c"

    suspend operator fun invoke(): Weather? {
        val location = locationProvider.getLocation() ?: return null
        val dto = api.getWeather(location.latitude, location.longitude, API_KEY)
        return dto.toDomain()
    }
}
