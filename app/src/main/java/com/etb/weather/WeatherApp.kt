package com.etb.weather

import android.app.Activity
import android.app.Application
import com.etb.weather.di.DaggerAppComponent
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

class WeatherApp: Application(), HasActivityInjector {

    @Inject
    lateinit var activityDispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()
        resolveDependencyGraph()
    }

    override fun activityInjector() = activityDispatchingAndroidInjector

    private fun resolveDependencyGraph() {
        DaggerAppComponent
                .builder()
                .application(this)
                .build()
                .inject(this)
    }
}
