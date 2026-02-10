package com.example.weatherapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.weatherapplication.presentation.weather.WeatherIntent
import com.example.weatherapplication.presentation.weather.WeatherState
import com.example.weatherapplication.presentation.weather.WeatherViewModel
import com.example.weatherapplication.ui.theme.WeatherApplicationTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WeatherApplicationTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    WeatherScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}


@Composable
fun WeatherScreen(viewModel: WeatherViewModel = hiltViewModel(), modifier: Modifier = Modifier) {

    val state by viewModel.state.collectAsStateWithLifecycle()
    var city by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        OutlinedTextField(
            value = city,
            onValueChange = { city = it },
            label = { Text("Enter City") }
        )

        Spacer(Modifier.height(8.dp))

        Button(onClick = {
            viewModel.sendIntent(WeatherIntent.SearchCity(city))
        }) {
            Text("Search")
        }

        Spacer(Modifier.height(16.dp))

        when(state){

            WeatherState.Loading -> CircularProgressIndicator()

            is WeatherState.Success -> {
                val weather = (state as WeatherState.Success).weather

                Text(weather.city, fontSize = 22.sp)
                Text("${weather.temperature} °C")
                Text("Humidity: ${weather.humidity}%")
                Text(weather.description)

                AsyncImage(
                    model = "https://openweathermap.org/img/wn/${weather.icon}@2x.png",
                    contentDescription = null
                )
            }

            is WeatherState.Error ->
                Text((state as WeatherState.Error).message)

            else -> {}
        }
    }
}





