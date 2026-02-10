package com.example.weatherapplication.presentation.weather

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapplication.domain.usecase.GetWeatherUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val getWeatherUseCase: GetWeatherUseCase
) : ViewModel() {

    private val intents = MutableSharedFlow<WeatherIntent>()

    private val _state = MutableStateFlow<WeatherState>(WeatherState.Idle)
    val state: StateFlow<WeatherState> = _state

    init { processIntents() }

    fun sendIntent(intent: WeatherIntent) {
        viewModelScope.launch { intents.emit(intent) }
    }

    private fun processIntents() {
        viewModelScope.launch {
            intents.collect { intent ->
                when(intent){
                    is WeatherIntent.SearchCity -> fetchWeather(intent.city)
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
}
