package com.etb.weather.di

import android.app.Activity
import com.etb.weather.ui.MainActivity
import dagger.Binds
import dagger.Module
import dagger.android.ActivityKey
import dagger.android.AndroidInjector
import dagger.multibindings.IntoMap

@Module(subcomponents = [MainComponent::class])
interface ActivityBinder {

    @Binds
    @IntoMap
    @ActivityKey(MainActivity::class)
    fun bindMain(builder: MainComponent.Builder): AndroidInjector.Factory<out Activity>

}
