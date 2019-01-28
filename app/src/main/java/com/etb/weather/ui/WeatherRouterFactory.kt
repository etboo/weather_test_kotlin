package com.etb.weather.ui

import android.support.v4.app.Fragment
import com.etb.weather.R
import com.etb.weather.common.Route
import com.etb.weather.common.Router
import com.etb.weather.common.RouterFactory
import com.etb.weather.ui.forecast.ForecastFragment
import com.etb.weather.ui.list.CityListFragment


private const val FORECAST_FRAGMENT = "ForecastFragment"

class WeatherRouterFactory(
        private val activity: MainActivity
) : RouterFactory {

    override fun initialRoute() = Route<Unit> {
        activity.supportFragmentManager.beginTransaction()
                .replace(R.id.container, CityListFragment.newInstance())
                .commitNow()
    }

    override fun <F: Fragment> from(fragment: F): Router<F> {
        return when (fragment) {
            is CityListFragment -> CityListRouter(activity) as Router<F>
            is ForecastFragment -> ForecastRouter(activity) as Router<F>
            else -> throw IllegalArgumentException()
        }
    }

    private class CityListRouter(
            private val activity: MainActivity
    ) : Router<CityListFragment> {
        override fun <M> act(action: Action<CityListFragment, M>): Route<M> {
            return when (action as ListAction) {
                is ForecastSelected  -> Route {
                    activity.supportFragmentManager.beginTransaction()
                            .replace(R.id.container, ForecastFragment.newInstance(it as String))
                            .addToBackStack(FORECAST_FRAGMENT)
                            .commitNow()

                }
                is BackFromList -> Route {
                    activity.showExitToast()
                }
                is BackFromExitDialog -> Route {
                    activity.finish()
                }
            }
        }
    }

    private class ForecastRouter(
            private val activity: MainActivity
    ) : Router<ForecastFragment> {
        override fun <M> act(action: Action<ForecastFragment, M>): Route<M> {
            return when (action as ForecastAction) {
                is BackFromForecast -> Route {
                    activity.supportFragmentManager.popBackStack()
                }
            }
        }

    }
}

