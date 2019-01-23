package com.etb.weather.di

import android.support.v4.app.Fragment
import com.etb.weather.ui.forecast.ForecastFragment
import com.etb.weather.ui.forecast.di.ForecastComponent
import com.etb.weather.ui.list.CityListFragment
import com.etb.weather.ui.list.di.CityListComponent
import dagger.Binds
import dagger.Module
import dagger.android.AndroidInjector
import dagger.android.support.FragmentKey
import dagger.multibindings.IntoMap

@Module(subcomponents = [
    CityListComponent::class,
    ForecastComponent::class
])
interface FragmentBinder {

    @Binds
    @IntoMap
    @FragmentKey(CityListFragment::class)
    fun bindCityList(builder: CityListComponent.Builder): AndroidInjector.Factory<out Fragment>

    @Binds
    @IntoMap
    @FragmentKey(ForecastFragment::class)
    fun bindForecast(builder: ForecastComponent.Builder): AndroidInjector.Factory<out Fragment>

}
