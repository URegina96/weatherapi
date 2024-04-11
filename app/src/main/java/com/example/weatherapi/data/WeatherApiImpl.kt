package com.example.weather.data

class WeatherApiImpl(private val apiService: WeatherApiService): WeatherApi{
    override suspend fun getCurrentWeather(city: String, apiKey:String): Weather {
        return apiService.getCurrentWeather(city, apiKey)
    }

    override suspend fun getWeeklyWeather(city: String, apiKey: String): List<Weather> {
        return apiService.getWeeklyWeather(city, apiKey)
    }
}