package com.example.weather

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.weather.presentation.WeatherViewModel
import com.example.weatherapi.R

class WeatherActivity : AppCompatActivity() {
    private  lateinit var  viewModel: WeatherViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_weather)

        viewModel.currentWeather.observe(this,{ currentWeather ->
            // обновление UI с текущим прогнозом погоды
            val textViewCity = findViewById<TextView>(R.id.editTextCity)
            val textViewTemperature = findViewById<TextView>(R.id.textViewTemperature)
            val textViewDescription = findViewById<TextView>(R.id.textViewDescription)

            textViewCity.text = "City: ${currentWeather.city}"
            textViewTemperature.text="Temperature: ${currentWeather.temperature}"
            textViewDescription.text="Description: ${currentWeather.description}"
        })

        viewModel.errorMessage.observe(this,{
            // обработка ошибок при получении данных погоды
            Toast.makeText(this,it,Toast.LENGTH_LONG).show()
        })
    }
}