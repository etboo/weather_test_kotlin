package com.etb.weather.model

data class Forecast(
        val name: String,
        val millis: Long,
        val dt: Long,
        val main: Details,
        val weather: List<Weather>
)

data class Details(
        val temp: Float,
        val humidity: Int,
        val pressure: Float,
        val temp_max: Float,
        val temp_min: Float
)

data class Weather(
        val id: Long,
        val main: String
)
