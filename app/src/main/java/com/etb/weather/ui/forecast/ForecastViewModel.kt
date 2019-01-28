package com.etb.weather.ui.forecast

import com.etb.weather.common.BaseViewModel
import com.etb.weather.common.UseCase
import com.etb.weather.common.disposeWith
import com.etb.weather.common.whenIs
import com.etb.weather.di.MAIN_SCHEDULER
import com.etb.weather.ui.forecast.di.FORECAST_CITY
import com.etb.weather.ui.forecast.di.FORECAST_USE_CASE
import com.etb.weather.ui.forecast.presentation.ForecastError
import com.etb.weather.ui.forecast.presentation.ForecastLoaded
import com.etb.weather.ui.forecast.presentation.ForecastLoading
import com.etb.weather.ui.forecast.presentation.ForecastState
import com.jakewharton.rxrelay2.BehaviorRelay
import com.jakewharton.rxrelay2.PublishRelay
import io.reactivex.Observable
import io.reactivex.Scheduler
import javax.inject.Inject
import javax.inject.Named

class ForecastViewModel
@Inject constructor(
        @Named(FORECAST_CITY)city: String,
        @Named(FORECAST_USE_CASE) getForecast: UseCase<String, ForecastState>,
        @Named(MAIN_SCHEDULER) mainScheduler: Scheduler
) : BaseViewModel() {

    private val presentationRelay = BehaviorRelay.createDefault<ForecastState>(ForecastLoading)

    private val forecastFlap = presentationRelay
            .whenIs(ForecastLoaded::class.java)
            .map { it.forecast }

    //forecast
    val state: Observable<ForecastState> = presentationRelay.hide()

    val id = forecastFlap.map { it.id }

    val cityName = forecastFlap.map { it.city.name }

    val tempMin = forecastFlap.map { it.temp_min }

    val temp = forecastFlap.map { it.temp }

    val tempMax = forecastFlap.map { it.temp_max }

    //error
    val errorDetails = presentationRelay
            .whenIs(ForecastError::class.java)
            .map { it.errorPresentation }

    //inputs
    val backPress = PublishRelay.create<Unit>()
    val backToList: Observable<Unit>
        //TODO: add some action validation logic
    get() = backPress

    init {
        getForecast.invoke(city)
                .observeOn(mainScheduler)
                .subscribe(presentationRelay)
                .disposeWith(disposable)
    }
}
