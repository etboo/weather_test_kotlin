package com.etb.weather.ui.forecast

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import com.etb.weather.R
import com.etb.weather.common.BaseFragment
import com.etb.weather.ui.list.CityListFragment
import javax.inject.Inject

class ForecastFragment: BaseFragment<ForecastViewModel>() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override val layoutId = R.layout.fragment_forecast

    override fun provideViewModel() = ViewModelProviders.of(this, viewModelFactory)
            .get(ForecastViewModel::class.java)



    companion object {

        fun newInstance(): CityListFragment {
            return CityListFragment()
        }
    }
}