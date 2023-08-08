package com.ics342.labs

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(private val apiService: ApiService) : ViewModel() {

    private val _forecast = MutableStateFlow<Forecast?>(null)
    val forecast: StateFlow<Forecast?> = _forecast

    private val _currentWeather = MutableStateFlow<WeatherData?>(null)
    val currentWeather: StateFlow<WeatherData?> = _currentWeather

    private val _zipCode = MutableStateFlow("")
    val zipCode: StateFlow<String> = _zipCode

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun updateZipCode(newZip: String) {
        _zipCode.value = newZip
    }

    fun isValidZip(): Boolean {
        return _zipCode.value.length == 5 && _zipCode.value.all { it.isDigit() }
    }

    fun fetchForecast(zip: String, apiKey: String) = viewModelScope.launch {
        try {
            _forecast.value = apiService.getForecast(zip, apiKey)
        } catch (e: Exception) {

        }
    }

    fun fetchCurrentWeather(zip: String, apiKey: String) = viewModelScope.launch {
        try {
            Log.d("WeatherVM", "Fetching current weather for ZIP: $zip")
            _currentWeather.value = apiService.getCurrentWeather(zip, apiKey)
        } catch (e: Exception) {
            Log.e("WeatherVM", "Error fetching current weather", e)
            _error.value = "Error fetching current weather: ${e.localizedMessage}"
        }
    }

}