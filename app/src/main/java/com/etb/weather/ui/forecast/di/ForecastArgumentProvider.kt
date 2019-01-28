package com.etb.weather.ui.forecast.di

import com.etb.weather.di.scopes.FragmentScope
import dagger.Module
import dagger.Provides
import javax.inject.Named

const val FORECAST_CITY = "FORECAST CITY"

@Module
class ForecastArgumentProvider(private val city: String) {

    @Provides
    @FragmentScope
    @Named(FORECAST_CITY)
    fun provideCity(): String {
        return city
    }
}
