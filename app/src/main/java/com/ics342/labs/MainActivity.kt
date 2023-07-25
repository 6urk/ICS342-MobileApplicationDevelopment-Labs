package com.ics342.labs

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import coil.compose.AsyncImage
import com.ics342.labs.ui.theme.LabsTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val currentConditionsViewModel: CurrentConditionsViewModel by viewModels()
    private val forecastViewModel: ForecastViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LabsTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    Column {
                        CurrentConditionsView(currentConditionsViewModel)
                        ForecastView(forecastViewModel)
                    }
                }
            }
        }
    }
}

@Composable
fun CurrentConditionsView(
    viewModel: CurrentConditionsViewModel
) {
    val currentWeather = viewModel.currentWeather.collectAsState(null)

    LaunchedEffect(Unit) {
        viewModel.fetchCurrentWeather("55407", "+1", "a2d3351eaf14e522f80fe5130d49e421")
    }

    currentWeather.value?.let { weather ->
        Column {
            Text(text = "High Temp: ${weather.highTemp}")
            WeatherConditionIcon(url = weather.iconUrl)
        }
    }
}

@Composable
fun ForecastView(
    viewModel: ForecastViewModel
) {
    val forecast = viewModel.forecast.collectAsState(null)

    LaunchedEffect(Unit) {
        viewModel.fetchForecast("55407", "+1", "a2d3351eaf14e522f80fe5130d49e421")
    }

    forecast.value?.let { forecastData ->
        Column {
            Text("Forecast")
            forecastData.forecasts.forEach { forecastItem ->
                WeatherConditionIcon(url = forecastItem.iconUrl)
            }
        }
    }
}

@Composable
fun WeatherConditionIcon(
    url: String
) {
    AsyncImage(model = url, contentDescription = "")
}