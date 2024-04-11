package com.example.weather.presentation

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weather.data.Weather
import com.example.weather.doman.CurrentWeather
import com.example.weather.doman.WeatherRepository
import com.example.weather.doman.WeeklyWeather
import kotlinx.coroutines.launch

// ViewModel  для отображения погоды
class WeatherViewModel(private val repository: WeatherRepository) : ViewModel() {
    val currentWeather = MutableLiveData<CurrentWeather>()
    val weeklyWeather = MutableLiveData<WeeklyWeather>()
    val errorMessage = MutableLiveData<String>()

    private val apiKey = "///"

    fun fetchCurrentWeather(city: String) {
        viewModelScope.launch {
           try {
               currentWeather.value = repository.getCurrentWeather(city, apiKey)
           } catch (e:Exception){
               errorMessage.value = "Error fetching current weather data: ${e.message}"
               Log.e("Error", errorMessage.toString())
           }
        }
    }

    fun fetchWeeklyWeather(city: String) {
        viewModelScope.launch {
            try {
            weeklyWeather.value = repository.getWeeklyWeather(city, apiKey)
            }catch (e:Exception){
                errorMessage.value = "Error fetching weekly weather data: ${e.message}"
                Log.e("Error", errorMessage.toString())
            }
        }
    }
    fun fetchWeatherForCity(city: String) {
        viewModelScope.launch {
            try {
                weeklyWeather.value = repository.getWeatherForCity(city, apiKey)
            } catch (e: Exception) {
                errorMessage.value = "Error fetching weather for city: $city"
                Log.e("Error", errorMessage.toString())
            }
        }
    }
}