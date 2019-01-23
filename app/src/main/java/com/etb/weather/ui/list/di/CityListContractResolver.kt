package com.etb.weather.ui.list.di

import android.arch.lifecycle.ViewModel
import com.etb.weather.di.ContractResolver
import com.etb.weather.di.ViewModelKey
import com.etb.weather.ui.list.CityListViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class CityListContractResolver: ContractResolver() {

    @Binds
    @IntoMap
    @ViewModelKey(CityListViewModel::class)
    abstract fun bindCityListViewModel(viewModel: CityListViewModel) : ViewModel
}
