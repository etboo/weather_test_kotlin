package com.etb.weather.ui.list.di

import com.etb.weather.di.scopes.FragmentScope
import com.etb.weather.ui.list.CityListFragment
import dagger.Subcomponent
import dagger.android.AndroidInjector

@FragmentScope
@Subcomponent(modules = [
    CityListContractResolver::class,
    CityListServicesModule::class
])
interface CityListComponent : AndroidInjector<CityListFragment> {
    @Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<CityListFragment>() {
    }

}