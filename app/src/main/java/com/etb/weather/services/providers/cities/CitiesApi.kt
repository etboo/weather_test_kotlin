package com.etb.weather.services.providers.cities

import com.etb.weather.model.PlacesResult
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query


interface CitiesApi {

    //To search City
    @GET("autocomplete/json")
    fun autocompleteCity(
            @Query("key") key: String,
            @Query("types") types: String,
            @Query("input") input: String): Single<PlacesResult>
}
