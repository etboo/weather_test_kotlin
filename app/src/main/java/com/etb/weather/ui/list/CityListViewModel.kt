package com.etb.weather.ui.list

import com.etb.weather.common.*
import com.etb.weather.di.MAIN_SCHEDULER
import com.etb.weather.ui.list.di.CITIES_USE_CASE
import com.etb.weather.ui.list.presentation.ListError
import com.etb.weather.ui.list.presentation.ListLoaded
import com.etb.weather.ui.list.presentation.ListState
import com.etb.weather.ui.list.presentation.Tutorial
import com.jakewharton.rxrelay2.BehaviorRelay
import com.jakewharton.rxrelay2.PublishRelay
import com.jakewharton.rxrelay2.Relay
import io.reactivex.Observable
import io.reactivex.Scheduler
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Named

private const val BACK_PRESS_TIME_PERIOD_SEC = 2L


class CityListViewModel
@Inject constructor(
        @Named(CITIES_USE_CASE) getCities: UseCase<String, ListState>,
        @Named(MAIN_SCHEDULER) mainScheduler: Scheduler
) : BaseViewModel() {

    //view states
    private val presentationRelay = BehaviorRelay.createDefault<ListState>(Tutorial)

    val state: Observable<ListState> = presentationRelay.hide()

    val listItems: Observable<List<String>> = presentationRelay
            .whenIs(ListLoaded::class.java)
            .map { it.items }

    val errorDetails: Observable<ErrorDetails> = presentationRelay
            .whenIs(ListError::class.java)
            .map { it.errorPresentation }

    //inputs
    val searchTextChanged: Relay<String> = PublishRelay.create<String>()

    //back pressed ->
    val backPress: Relay<Unit> = PublishRelay.create<Unit>()
    //<-
    val showExitToast: Observable<Unit>
        get() = backPress.throttleFirst(BACK_PRESS_TIME_PERIOD_SEC, TimeUnit.SECONDS)
    val quitApp: Observable<Unit>
        get() = backPress.buffer(BACK_PRESS_TIME_PERIOD_SEC, TimeUnit.SECONDS, 2)
                .filter { it.size == 2 }
                .map { Unit }

    //list item clicked ->
    val listItemClick: Relay<Int> = PublishRelay.create<Int>()
    //<-
    val citySelected: Observable<String>
        get() = listItemClick.switchMap { index -> listItems.map { it[index] } }

    init {
        searchTextChanged
                .filter { !it.isEmpty() }
                .debounce(300, TimeUnit.MILLISECONDS)
                .switchMap(getCities)
                .observeOn(mainScheduler)
                .subscribe(presentationRelay)
                .disposeWith(disposable)
    }
}
