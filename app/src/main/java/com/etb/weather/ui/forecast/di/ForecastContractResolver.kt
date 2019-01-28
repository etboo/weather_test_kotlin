package com.etb.weather.ui.forecast.di

import android.arch.lifecycle.ViewModel
import com.etb.weather.di.ContractResolver
import com.etb.weather.di.ViewModelKey
import com.etb.weather.ui.forecast.ForecastViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ForecastContractResolver: ContractResolver() {

    @Binds
    @IntoMap
    @ViewModelKey(ForecastViewModel::class)
    abstract fun bindForecastViewModel(viewModel: ForecastViewModel) : ViewModel
}

