package com.etb.weather.services.providers.cities

import android.util.Log
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class CitiesNetwork(
        val apiKey: String,
        val api: CitiesApi
): CitiesProvider {
    override fun getCities(query: String): Single<List<String>> {
        return api.autocompleteCity(apiKey, "(cities)", query)
                .doOnEvent { t1, t2 -> Log.d("TAG", "TAG") }
                .subscribeOn(Schedulers.io())
                .map { it.predictions?.mapNotNull { it.description } }
    }
}
