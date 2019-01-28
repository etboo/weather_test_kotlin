package com.etb.weather.ui.list.presentation

import com.etb.weather.R
import com.etb.weather.common.Presenter
import javax.inject.Inject


class ListStatePresenter @Inject constructor()
    : Presenter<ListState, Int> {
    override fun invoke(state: ListState) = when (state) {
        is Tutorial -> R.id.citiesListTutorial
        is ListLoading -> R.id.citiesListLoading
        is ListLoaded -> R.id.citiesListRv
        is ListEmpty -> R.id.citiesListEmpty
        is ListError -> R.id.citiesListError
    }
}
