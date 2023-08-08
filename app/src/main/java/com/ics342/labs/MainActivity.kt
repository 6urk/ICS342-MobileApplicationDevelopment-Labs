package com.ics342.labs

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.ics342.labs.ui.theme.LabsTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val weatherViewModel: WeatherViewModel by viewModels()

    @SuppressLint("StateFlowValueCalledInComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            LabsTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        val error = weatherViewModel.error.collectAsState().value
                        error?.let {
                            Text(text = it, color = MaterialTheme.colorScheme.error)
                        }
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            CurrentConditionsView(weatherViewModel)
                            ForecastView(weatherViewModel, weatherViewModel.zipCode.value)
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrentConditionsView(viewModel: WeatherViewModel) {
    val currentWeather = viewModel.currentWeather.collectAsState(null).value
    val zipCode = viewModel.zipCode.collectAsState().value
    var showDialog by remember { mutableStateOf(false) }

    fun onSearch() {
        if (viewModel.isValidZip()) {
            viewModel.fetchCurrentWeather(zipCode, "30d2fb90b0aed6210364682160fd2a45")
            viewModel.fetchForecast(zipCode, "30d2fb90b0aed6210364682160fd2a45")
        } else {
            showDialog = true
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Invalid ZIP code") },
            text = { Text("Please enter a valid 5-digit ZIP code.") },
            confirmButton = {
                Button(onClick = { showDialog = false }) {
                    Text("OK")
                }
            }
        )
    }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        TextField(
            value = zipCode,
            onValueChange = {
                if (it.length <= 5 && it.all { char -> char.isDigit() }) {
                    viewModel.updateZipCode(it)
                }
            },
            label = { Text(text = "Enter ZIP code") },
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(onDone = {
                onSearch()
            })
        )

        Button(onClick = { onSearch() }) {
            Text("Search")
        }

        Spacer(modifier = Modifier.height(8.dp))  // Reduced spacer for aesthetics

        currentWeather?.let { weather ->
            Text(text = "Temperature: ${String.format("%.2f", kelvinToFahrenheit(weather.highTemp))}\u00B0F")
            WeatherConditionIcon(url = weather.iconUrl)
        }
    }
}

@Composable
fun ForecastView(viewModel: WeatherViewModel, zipCode: String) {
    val forecast = viewModel.forecast.collectAsState(null)

    LaunchedEffect(zipCode) {
        viewModel.fetchForecast(zipCode, "30d2fb90b0aed6210364682160fd2a45")
    }

    forecast.value?.let { forecastData ->
        Text("Forecast:")
        Row(horizontalArrangement = Arrangement.SpaceBetween) {
            forecastData.forecasts.forEach { forecastItem ->
                WeatherConditionIcon(url = forecastItem.iconUrl)
            }
        }
    }
}

@Composable
fun WeatherConditionIcon(url: String) {
    AsyncImage(model = url, contentDescription = "")
}


