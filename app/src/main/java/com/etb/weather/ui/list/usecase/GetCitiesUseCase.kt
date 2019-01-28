package com.etb.weather.ui.list.usecase

import com.etb.weather.common.ErrorDetails
import com.etb.weather.common.UseCase
import com.etb.weather.services.providers.cities.CitiesProvider
import com.etb.weather.ui.list.presentation.*
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

class GetCitiesUseCase(
        private val network: CitiesProvider
): UseCase<String, ListState> {

    override fun invoke(query: String): Observable<ListState> {
        return network.getCities(query)
                .map {
                    if(it.isEmpty()) {
                        ListEmpty
                    } else {
                        ListLoaded(it)
                    }
               }
                .timeout(10, TimeUnit.SECONDS)
                .onErrorReturn { ListError(ErrorDetails("Network Error", it.localizedMessage)) }
                .toObservable()
                .startWith(ListLoading)
    }
}