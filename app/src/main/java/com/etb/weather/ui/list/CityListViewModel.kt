package com.etb.weather.ui.list

import com.etb.weather.common.BaseViewModel
import com.etb.weather.common.ErrorDetails
import com.etb.weather.common.UseCase
import com.etb.weather.common.disposeWith
import com.etb.weather.di.MAIN_SCHEDULER
import com.etb.weather.ui.list.di.CITIES_USE_CASE
import com.etb.weather.ui.list.presentation.ListError
import com.etb.weather.ui.list.presentation.ListLoaded
import com.etb.weather.ui.list.presentation.ListState
import com.etb.weather.ui.list.presentation.Tutorial
import com.jakewharton.rxrelay2.BehaviorRelay
import com.jakewharton.rxrelay2.PublishRelay
import io.reactivex.Observable
import io.reactivex.Scheduler
import javax.inject.Inject
import javax.inject.Named

class CityListViewModel
@Inject constructor(
        @Named(CITIES_USE_CASE) getCities: UseCase<@JvmWildcard String, @JvmWildcard Observable<@JvmWildcard ListState>>,
        @Named(MAIN_SCHEDULER) mainScheduler: Scheduler
) : BaseViewModel() {

    private val presentationRelay = BehaviorRelay.createDefault<ListState>(Tutorial)

    val state: Observable<ListState> = presentationRelay.hide()

    val listItems: Observable<List<String>> = presentationRelay
            .switchMap {
                when (it) {
                    is ListLoaded -> Observable.just(it.items)
                    else -> Observable.empty()
                }
            }

    val errorDetails: Observable<ErrorDetails> = presentationRelay
            .switchMap {
                when (it) {
                    is ListError -> Observable.just(it.errorPresentation)
                    else -> Observable.empty()
                }
            }

    val searchTextChanged = PublishRelay.create<String>()

    init {
        searchTextChanged
                .filter { !it.isEmpty() }
                .flatMap(getCities)
                .observeOn(mainScheduler)
                .subscribe(presentationRelay)
                .disposeWith(disposable)
    }
}
