package com.etb.weather.di

import com.etb.weather.ui.MainActivity
import com.gismart.karaoke.di.scopes.ActivityScope
import dagger.Subcomponent
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector

@ActivityScope
@Subcomponent(modules = [
    AndroidInjectionModule::class,
    FragmentBinder::class
])
interface MainComponent : AndroidInjector<MainActivity> {
    @Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<MainActivity>() {

    }

}
