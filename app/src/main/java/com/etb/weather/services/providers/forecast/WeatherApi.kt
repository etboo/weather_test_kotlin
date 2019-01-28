package com.etb.weather.services.providers.forecast

import com.etb.weather.model.ForecastResult
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query


interface WeatherApi {

    @GET("/data/2.5/find?mode=json&type=like&cnt=10")
    fun getWeather(@Query("q") city: String): Single<ForecastResult>

}
