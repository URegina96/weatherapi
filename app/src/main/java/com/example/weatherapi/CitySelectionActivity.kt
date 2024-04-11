package com.example.weather

import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.weather.presentation.WeatherViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import android.Manifest
import com.example.weather.data.WeatherApiService
import com.example.weather.doman.WeatherRepository
import com.example.weatherapi.R

class CitySelectionActivity : AppCompatActivity() {
    private lateinit var viewModel: WeatherViewModel
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_city_selection)

        val repository = WeatherRepository(WeatherApiService())
        val viewModelFactory = WeatherViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(WeatherViewModel::class.java)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        val editTextCity = findViewById<EditText>(R.id.editTextCity)
        val buttonSelectCity = findViewById<Button>(R.id.buttonSelectCity)

        buttonSelectCity.setOnClickListener {
            val city = editTextCity.text.toString()
            viewModel.fetchCurrentWeather(city)
            viewModel.fetchWeeklyWeather(city)
        }
        buttonSelectCity.setOnClickListener {
            val city = editTextCity.text.toString()
            viewModel.fetchCurrentWeather(city)
            viewModel.fetchWeatherForCity(city)
        }

        // запрос на разрешшение использования геолокации
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
        } else {
            getCurrentLocation()
        }

        viewModel.errorMessage.observe(this, {
            //обработка ошибок при получении данных погоды
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
        })
    }

    private fun getCurrentLocation() {
        // получение текущего местоположения пользователя
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: android.location.Location? ->
                // Здесь можно использовать полученные координаты для определения текущего города
                // Например, использовать геокодирование для получения названия города по координатам
            }
            .addOnFailureListener { e ->
                // обработка ошибок при получении местоположения
                Toast.makeText(this, "Error getting location: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1001
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getCurrentLocation() // Если разрешение получено, получаем текущее местоположение
            } else {
                Toast.makeText(this, "Location permission denied", Toast.LENGTH_SHORT).show()
            }
        }
    }
}