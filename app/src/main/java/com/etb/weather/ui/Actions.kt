package com.etb.weather.ui

import android.support.v4.app.Fragment
import com.etb.weather.ui.forecast.ForecastFragment
import com.etb.weather.ui.list.CityListFragment

sealed class Action<F : Fragment, M>

//city list actions
sealed class ListAction<M> : Action<CityListFragment, M>()

object ForecastSelected : ListAction<String>()
object BackFromList : ListAction<Unit>()
object BackFromExitDialog: ListAction<Unit>()

//forecast actions
sealed class ForecastAction<M>: Action<ForecastFragment, M>()

object BackFromForecast : ForecastAction<Unit>()









