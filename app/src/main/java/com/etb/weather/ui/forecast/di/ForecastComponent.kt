package com.etb.weather.ui.forecast.di

import com.etb.weather.di.scopes.FragmentScope
import com.etb.weather.ui.forecast.ForecastFragment
import dagger.Subcomponent
import dagger.android.AndroidInjector

@FragmentScope
@Subcomponent(modules = [
    ForecastContractResolver::class,
    ForecastServicesModule::class,
    ForecastArgumentProvider::class
])
interface ForecastComponent : AndroidInjector<ForecastFragment> {
    @Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<ForecastFragment>() {
        abstract fun argument(argumentProvider: ForecastArgumentProvider): Builder

        override fun seedInstance(instance: ForecastFragment) {
            argument(ForecastArgumentProvider(instance.city))
        }
    }
}
