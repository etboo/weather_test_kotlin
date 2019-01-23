package com.etb.weather.ui

import com.etb.weather.model.City

sealed class Screen

object CityList: Screen()
class CityForecast(city: City): Screen()
