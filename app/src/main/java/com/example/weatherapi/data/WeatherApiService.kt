package com.example.weather.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

// планировалось как  интерфейс для работы с сетью и выполнения запросов к API в Data слое
class WeatherApiService {

//    private val apiKey = "//////"
    private var currentCity: String = ""

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.openweathermap.org")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val weatherApi = retrofit.create(WeatherApi::class.java)

    suspend fun getCurrentWeather(city: String, apiKey: String): Weather {
        currentCity = city // устанавливаем текущий город
        val response = weatherApi.getCurrentWeather(city, apiKey)
        return response
    }

    suspend fun getWeeklyWeather(city: String, apiKey: String): List<Weather> {
        // тут выполнение заппроса api и возврат данных о погоде на неделю
        val response = weatherApi.getWeeklyWeather(city, apiKey)
        return response
    }
    fun getCurrentCity(): String {
        return currentCity
    }
}