package com.etb.weather.ui.forecast

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.graphics.Typeface
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View
import com.etb.weather.R
import com.etb.weather.common.BaseFragment
import com.etb.weather.ui.BackFromForecast
import com.etb.weather.ui.MainActivity
import com.etb.weather.ui.forecast.presentation.IconPresenter
import com.jakewharton.rxbinding2.widget.text
import kotlinx.android.synthetic.main.fragment_forecast.view.*
import javax.inject.Inject

private const val CITY_KEY = "Forecast.City_Key"

class ForecastFragment : BaseFragment<ForecastViewModel>() {

    val city: String
    get() = arguments!!.getString(CITY_KEY)


    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var iconPresenter: IconPresenter

    override val layoutId = R.layout.fragment_forecast

    private val router by lazy { (activity as MainActivity).getRouterFor(this) }

    override fun initUI(view: View) {
        super.initUI(view)
        activity?.let {
            val font = Typeface.createFromAsset(it.assets, "fonts/weather.ttf")
            view.forecastIconTv.typeface = font
        }
    }

    override fun provideViewModel() = ViewModelProviders.of(this, viewModelFactory)
            .get(ForecastViewModel::class.java)

    override fun bind(view: View, vm: ForecastViewModel) = listOf(
            //vm -> view
            vm.cityName.subscribe(view.forecastCityTv.text()),
            vm.temp.map { it.toString() }
                    .subscribe(view.forecastCurrentTempTv.text()),
            vm.tempMin.map { it.toString() }
                    .subscribe(view.forecastMinTempTv.text()),
            vm.tempMax.map { it.toString() }
                    .subscribe(view.forecastMaxTv.text()),
            vm.id.map(iconPresenter)
                    .subscribe(view.forecastIconTv.text()),
            vm.backToList.subscribe(router.act(BackFromForecast)),
            //view -> vm
            backPress.subscribe(vm.backPress)
    )



    companion object {

        fun newInstance(city: String): Fragment {
            return ForecastFragment().also {
                val forecastArgs = Bundle().apply { putString(CITY_KEY, city) }
                it.arguments = forecastArgs
            }
        }
    }
}