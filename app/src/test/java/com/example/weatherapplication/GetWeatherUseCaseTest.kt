package com.example.weatherapplication

import com.example.weatherapplication.domain.Weather
import com.example.weatherapplication.domain.repository.WeatherRepository
import com.example.weatherapplication.domain.usecase.GetWeatherUseCase
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GetWeatherUseCaseTest {

    private val repository: WeatherRepository = mockk()
    private lateinit var useCase: GetWeatherUseCase

    @Before
    fun setup() {
        useCase = GetWeatherUseCase(repository)
    }

    @Test
    fun `returns weather from repository`() = runTest {

        val weather = Weather(
            city = "Austin",
            temperature = 28.0,
            humidity = 50,
            description = "cloudy",
            icon = "02d"
        )

       // coEvery { repository.getWeather("Austin") } returns weather

        val result = useCase("Austin")

        assertEquals("Austin", result.city)
    }
}
