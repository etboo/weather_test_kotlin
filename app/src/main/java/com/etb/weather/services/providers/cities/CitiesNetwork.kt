package com.etb.weather.services.providers.cities

import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class CitiesNetwork(
        val apiKey: String,
        val api: CitiesApi
): CitiesProvider {
    override fun getCities(query: String): Single<List<String>> {
        return api.autocompleteCity(apiKey, "(cities)", query)
                .subscribeOn(Schedulers.io())
                .map { it.predictions?.mapNotNull { it.description } }
    }
}
