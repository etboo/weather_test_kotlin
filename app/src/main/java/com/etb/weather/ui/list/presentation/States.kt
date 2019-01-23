package com.etb.weather.ui.list.presentation

import com.etb.weather.common.ErrorDetails


sealed class ListState

object Tutorial: ListState()
object ListLoading : ListState()
data class ListLoaded(val items: List<String>) : ListState()
object ListEmpty: ListState()
data class ListError(val errorPresentation: ErrorDetails) : ListState()
