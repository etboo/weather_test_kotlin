package com.etb.weather.services.providers.forecast

import com.etb.weather.model.Forecast
import io.reactivex.Single

class ForecastNetwork(
        private val api: WeatherApi
): ForecastProvider {
    override fun getForecastFor(city: String): Single<Forecast> {
        return api.getWeather(city).map { it.first() }
    }

}
