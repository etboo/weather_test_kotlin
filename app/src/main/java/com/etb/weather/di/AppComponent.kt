package com.etb.weather.di

import com.etb.weather.WeatherApp
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidInjectionModule::class,
    DatabaseModule::class,
    NetworkModule::class,
    AppModule::class,
    ActivityBinder::class
])
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(app: WeatherApp): Builder

        fun build(): AppComponent
    }

    fun inject(activity: WeatherApp)

}
