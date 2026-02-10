package com.example.weatherapplication.data.remote.local

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LastCityStorage @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val Context.dataStore by preferencesDataStore("weather")

    private val KEY_LAST_CITY = stringPreferencesKey("last_city")

    suspend fun saveCity(city: String) {
        context.dataStore.edit {
            it[KEY_LAST_CITY] = city
        }
    }

    val lastCity: Flow<String?> =
        context.dataStore.data.map { it[KEY_LAST_CITY] }
}
