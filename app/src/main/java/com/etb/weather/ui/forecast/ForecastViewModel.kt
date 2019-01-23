package com.etb.weather.ui.forecast

import com.etb.weather.common.BaseViewModel
import com.etb.weather.common.UseCase
import com.etb.weather.common.disposeWith
import com.etb.weather.common.flap
import com.etb.weather.di.MAIN_SCHEDULER
import com.etb.weather.ui.forecast.di.FORECAST_USE_CASE
import com.etb.weather.ui.forecast.presentation.ForecastError
import com.etb.weather.ui.forecast.presentation.ForecastLoaded
import com.etb.weather.ui.forecast.presentation.ForecastLoading
import com.etb.weather.ui.forecast.presentation.ForecastState
import com.jakewharton.rxrelay2.BehaviorRelay
import io.reactivex.Observable
import io.reactivex.Scheduler
import javax.inject.Inject
import javax.inject.Named

class ForecastViewModel
@Inject constructor(
        @Named(FORECAST_USE_CASE) getForecast: UseCase<@JvmWildcard String, @JvmWildcard Observable<@JvmWildcard ForecastState>>,
        @Named(MAIN_SCHEDULER) mainScheduler: Scheduler
) : BaseViewModel() {

    private val presentationRelay = BehaviorRelay.createDefault<ForecastState>(ForecastLoading)

    private val forecastFlap = presentationRelay
            .flap(ForecastLoaded::class.java)
            .map { it.forecast }

    //forecast
    val state: Observable<ForecastState> = presentationRelay.hide()

    val cityName = forecastFlap.map { it.city.name }

    val tempMin = forecastFlap.map { it.temp_min }

    val temp = forecastFlap.map { it.temp }

    val tempMax = forecastFlap.map { it.temp_max }

    //error
    val errorDetails = presentationRelay
            .flap(ForecastError::class.java)
            .map { it.errorPresentation }

    init {
        getForecast.invoke("dasdsa")
                .observeOn(mainScheduler)
                .subscribe(presentationRelay)
                .disposeWith(disposable)
    }
}
