package com.example.weather

import android.os.Bundle
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.weather.presentation.WeatherViewModel
import com.example.weatherapi.R

class WeeklyWeatherActivity : AppCompatActivity() {
    private lateinit var viewModel: WeatherViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_weekly_weather)

        viewModel = ViewModelProvider(this).get(WeatherViewModel::class.java)

        viewModel.weeklyWeather.observe(this,{ weeklyWeather ->
            // обновление UI с пронозом погоды на неделю
            val textViewCityWeekly = findViewById<TextView>(R.id.textViewCityWeekly)
            val  listViewWeeklyForecast = findViewById<ListView>(R.id.listViewWeeklyForecast)

                //сюда можно поставить адаптек для отображения списка прогноза погоды на неделю  в  listViewWeeklyForecast
        })

        viewModel.errorMessage.observe(this, {
            // Обработка ошибок при получении данных погоды
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        })
    }
}