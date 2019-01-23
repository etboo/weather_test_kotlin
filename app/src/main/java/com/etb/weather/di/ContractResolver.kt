package com.etb.weather.di

import android.arch.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module

@Module
abstract class ContractResolver {

    @Binds
    internal abstract fun bindViewModelFactory(factory: DaggerViewModelFactory): ViewModelProvider.Factory

}

