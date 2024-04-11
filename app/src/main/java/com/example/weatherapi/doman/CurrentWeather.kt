package com.example.weather.doman

import com.example.weather.data.Weather
//натсройка  модели данных для прогноза погоды
data class CurrentWeather (val city: String, val temperature: Double, val description: String)

data class WeeklyWeather (val city: String,val forecasts: List<Weather>)