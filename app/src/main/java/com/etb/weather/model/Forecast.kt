package com.etb.weather.model

data class Forecast(
        val millis: Long,
        val id: Long,
        val city: City,
        val dt: Long,
        val clouds: Int,
        val humidity: Int,
        val pressure: Float,
        val temp_max: Float,
        val sea_level: Int,
        val temp_min: Float,
        val temp: Float,
        val grnd_level: Float,
        val sunrise: Long,
        val sunset: Long,
        val wind_speed: Float,
        val wind_deg: Float
)
