package com.etb.weather.ui.forecast.presentation

import android.content.Context
import com.etb.weather.R
import com.etb.weather.common.Presenter
import javax.inject.Inject

class IconPresenter @Inject constructor(private val context: Context)
    : Presenter<Long, String> {
    override fun invoke(actualId: Long): String {
        val id = if (actualId == 800L) {
            R.string.weather_sunny
        } else {
            getStringRes(actualId.toInt() / 100)
        }

        return context.getString(id)
    }

    private fun getStringRes(id: Int) = when (id) {
        2 -> R.string.weather_thunder
        3 -> R.string.weather_drizzle
        5 -> R.string.weather_rainy
        6 -> R.string.weather_snowy
        7 -> R.string.weather_foggy
        8 -> R.string.weather_cloudy
        else -> R.string.undefined_weather
    }

}