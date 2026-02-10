package com.example.weatherapplication.presentation.weather

object WeatherReducer {
    fun reduce(result: WeatherResult): WeatherState =
        when(result){
            WeatherResult.Loading -> WeatherState.Loading
            is WeatherResult.Success -> WeatherState.Success(result.weather)
            is WeatherResult.Error -> WeatherState.Error(result.message)
        }
}
