package com.etb.weather.ui.forecast.presentation

import com.etb.weather.common.ErrorDetails
import com.etb.weather.model.Forecast

sealed class ForecastState

object ForecastLoading: ForecastState()
class ForecastLoaded(val forecast : Forecast): ForecastState()
class ForecastError(val errorPresentation: ErrorDetails): ForecastState()