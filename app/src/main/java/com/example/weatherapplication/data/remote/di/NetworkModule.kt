package com.example.weatherapplication.data.remote.di

import com.example.weatherapplication.data.remote.WeatherAPI
import com.example.weatherapplication.data.remote.repository.WeatherRepositoryImpl
import com.example.weatherapplication.domain.repository.WeatherRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory



@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindWeatherRepository(
        impl: WeatherRepositoryImpl
    ): WeatherRepository
}



@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun provideRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    fun provideApi(retrofit: Retrofit): WeatherAPI =
        retrofit.create(WeatherAPI::class.java)
}
