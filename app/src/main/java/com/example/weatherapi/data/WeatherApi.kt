package com.example.weather.data

import com.google.android.gms.common.api.internal.ApiKey

// для работы с api погоды
interface WeatherApi {
    suspend fun getCurrentWeather(city: String, apiKey: String): Weather
    suspend fun getWeeklyWeather(city: String, apiKey: String): List<Weather>
}