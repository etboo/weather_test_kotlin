package com.etb.weather.services.providers.forecast

import com.etb.weather.model.Forecast
import io.reactivex.Single

interface ForecastProvider {

    fun getForecastFor(city: String): Single<Forecast>
}