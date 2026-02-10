package com.example.weatherapplication.presentation.weather

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapplication.data.remote.local.LastCityStorage
import com.example.weatherapplication.domain.usecase.GetWeatherByLocationUseCase
import com.example.weatherapplication.domain.usecase.GetWeatherUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val getWeatherUseCase: GetWeatherUseCase,
    private val getWeatherByLocationUseCase: GetWeatherByLocationUseCase,
    private val storage: LastCityStorage
) : ViewModel() {

    private val intents = MutableSharedFlow<WeatherIntent>()

    private val _state = MutableStateFlow<WeatherState>(WeatherState.Idle)
    val state: StateFlow<WeatherState> = _state

    private val _effect = MutableSharedFlow<WeatherEffect>()
    val effect = _effect.asSharedFlow()

    init {
        observeIntents()
        loadInitialWeather()
    }


    fun sendIntent(intent: WeatherIntent) {
        viewModelScope.launch { intents.emit(intent) }
    }

    private fun loadInitialWeather() {
        viewModelScope.launch {

            val lastCity = storage.lastCity.firstOrNull()

            if (lastCity != null) {
                sendIntent(WeatherIntent.SearchCity(lastCity))
            } else {
                _effect.emit(WeatherEffect.RequestLocationPermission)
            }
        }
    }

    private fun observeIntents() {
        viewModelScope.launch {
            intents.collect { intent ->
                when (intent) {
                    is WeatherIntent.SearchCity -> fetchWeather(intent.city)
                    WeatherIntent.LoadCurrentLocation -> fetchLocationWeather()
                    WeatherIntent.LoadLastCity -> loadLastCity()
                }
            }
        }
    }


    private suspend fun fetchWeather(city:String){
        emit(WeatherResult.Loading)

        try{
            val weather = getWeatherUseCase(city)
            emit(WeatherResult.Success(weather))
        }catch (e:Exception){
            emit(WeatherResult.Error("City not found"))
        }
    }

    private fun emit(result:WeatherResult){
        _state.value = WeatherReducer.reduce(result)
    }

    private suspend fun fetchLocationWeather() {

        emit(WeatherResult.Loading)

        try {
            val weather = getWeatherByLocationUseCase()

            if (weather != null)
                emit(WeatherResult.Success(weather))
            else
                emit(WeatherResult.Error("Unable to get location"))

        } catch (e: Exception) {
            emit(WeatherResult.Error("Location weather failed"))
        }
    }

    private suspend fun loadLastCity() {
        emit(WeatherResult.Loading)
        try {
            val lastCity = storage.lastCity.firstOrNull()
            if (!lastCity.isNullOrBlank()) {
                val weather = getWeatherUseCase(lastCity)
                emit(WeatherResult.Success(weather))
            } else {
                emit(WeatherResult.Error("No previous city found. Please search a city."))
            }

        } catch (e: Exception) {
            emit(WeatherResult.Error("Failed to load last city weather"))
        }
    }


}
