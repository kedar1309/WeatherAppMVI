package com.example.weatherapplication

import com.example.weatherapplication.domain.Weather
import com.example.weatherapplication.presentation.weather.WeatherReducer
import com.example.weatherapplication.presentation.weather.WeatherResult
import com.example.weatherapplication.presentation.weather.WeatherState
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import org.junit.Test

class WeatherReducerTest {

    @Test
    fun `Loading result produces Loading state`() {
        val state = WeatherReducer.reduce(WeatherResult.Loading)
        assertTrue(state is WeatherState.Loading)
    }

    @Test
    fun `Success result produces Success state`() {
        val weather = Weather(
            city = "Dallas",
            temperature = 30.0,
            humidity = 40,
            description = "clear sky",
            icon = "01d"
        )

        val state = WeatherReducer.reduce(WeatherResult.Success(weather))

        assertTrue(state is WeatherState.Success)
        assertEquals("Dallas", (state as WeatherState.Success).weather.city)
    }

    @Test
    fun `Error result produces Error state`() {
        val state = WeatherReducer.reduce(WeatherResult.Error("Network error"))

        assertTrue(state is WeatherState.Error)
        assertEquals("Network error", (state as WeatherState.Error).message)
    }
}
