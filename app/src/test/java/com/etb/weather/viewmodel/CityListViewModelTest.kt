package com.etb.weather.viewmodel

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.etb.weather.common.UseCase
import com.etb.weather.comon.mock
import com.etb.weather.comon.whenever
import com.etb.weather.ui.list.CityListViewModel
import com.etb.weather.ui.list.presentation.ListEmpty
import com.etb.weather.ui.list.presentation.ListLoading
import com.etb.weather.ui.list.presentation.ListState
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentMatchers.anyString

class CityListViewModelTest {

    abstract class TestUseCase
        : UseCase<String, Observable<ListState>>


    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    val useCase = mock<TestUseCase>()

    val viewModel by lazy { CityListViewModel(useCase, Schedulers.trampoline()) }

    @Test
    fun testCityListViewModel_getCities_Empty() {
        whenever(useCase.invoke(anyString()))
                .thenReturn(Observable.just(ListEmpty))

        viewModel.state
                .test()
                .assertValue(ListEmpty)
    }

    @Test
    fun testCityListViewModel_getCities_Loading() {
        whenever(useCase.invoke(anyString()))
                .thenReturn(Observable.never())

        viewModel.state
                .test()
                .assertValue(ListLoading)
    }



}
