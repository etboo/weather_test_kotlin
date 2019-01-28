package com.etb.weather.ui

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.etb.weather.R
import com.etb.weather.common.Router
import com.jakewharton.rxrelay2.PublishRelay
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

class MainActivity : AppCompatActivity(), HasSupportFragmentInjector {

    val routerFactory by lazy { WeatherRouterFactory(this) }

    val backPressed = PublishRelay.create<Unit>()

    @Inject
    lateinit var fragmentInjector: DispatchingAndroidInjector<Fragment>

    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
        return fragmentInjector
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            routerFactory.initialRoute()
                    .accept(Unit)
        }
    }

    override fun onBackPressed() {
        backPressed.accept(Unit)
    }

    fun <F: Fragment> getRouterFor(fragment: F): Router<F> {
        return routerFactory.from(fragment)
    }

    fun showExitToast() {
        Toast.makeText(this, R.string.exit_toast, Toast.LENGTH_SHORT).show()
    }
}
