package com.etb.weather.services.providers.cities

import com.etb.weather.model.City
import io.reactivex.Single

interface CitiesProvider {

    fun getCities(query: String = ""): Single<List<String>>

}