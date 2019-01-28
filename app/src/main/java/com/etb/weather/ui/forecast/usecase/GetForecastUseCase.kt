package com.etb.weather.ui.forecast.usecase

import com.etb.weather.common.ErrorDetails
import com.etb.weather.common.UseCase
import com.etb.weather.services.providers.forecast.ForecastProvider
import com.etb.weather.ui.forecast.presentation.ForecastError
import com.etb.weather.ui.forecast.presentation.ForecastLoaded
import com.etb.weather.ui.forecast.presentation.ForecastLoading
import com.etb.weather.ui.forecast.presentation.ForecastState
import io.reactivex.Observable

class GetForecastUseCase(
        private val network: ForecastProvider

): UseCase<String, ForecastState> {

    override fun invoke(city: String): Observable<ForecastState> {
        return network.getForecastFor(city)
                .map { ForecastLoaded(it) as ForecastState }
                .onErrorReturn { ForecastError(ErrorDetails("Network error", it.localizedMessage)) }
                .toObservable()
                .startWith(ForecastLoading)
    }

}