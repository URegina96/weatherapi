package com.example.weather.doman

import com.example.weather.data.Weather
import com.example.weather.data.WeatherApiService

// репозиторий для работы с прогнозом погоды
class WeatherRepository (private val apiService:WeatherApiService){

    suspend fun getCurrentWeather(city:String, apiKey: String):CurrentWeather{
        val weather = apiService.getCurrentWeather(city, apiKey)
        return CurrentWeather(weather.city, weather.temperature, weather.description)
    }
    suspend fun getWeeklyWeather(city: String, apiKey: String): WeeklyWeather {
        val forecasts = apiService.getWeeklyWeather(city, apiKey)
        return WeeklyWeather(city, forecasts)
    }
    suspend fun getCurrentCityWeather(apiKey: String): CurrentWeather {
        val city = apiService.getCurrentCity()
        val weather = apiService.getCurrentWeather(city, apiKey)
        return CurrentWeather(weather.city, weather.temperature, weather.description)
    }

    suspend fun getWeatherForCity(city: String, apiKey: String): WeeklyWeather {
        val forecasts = apiService.getWeeklyWeather(city, apiKey)
        return WeeklyWeather(city, forecasts)
    }
}