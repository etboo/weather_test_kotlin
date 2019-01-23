package com.etb.weather.di

import android.content.Context
import com.etb.weather.WeatherApp
import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Named
import javax.inject.Singleton

const val MAIN_SCHEDULER = "MAIN_SCHEDULER"

@Module
class AppModule {

    @Provides
    @Singleton
    fun provideContext(app: WeatherApp): Context {
        return app.applicationContext
    }

    @Provides
    @Singleton
    @Named(MAIN_SCHEDULER)
    fun provideMainScheduler(): Scheduler {
        return AndroidSchedulers.mainThread()
    }

}
