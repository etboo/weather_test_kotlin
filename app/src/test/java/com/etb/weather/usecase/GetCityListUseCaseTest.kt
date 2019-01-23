package com.etb.weather.usecase

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.etb.weather.common.ErrorDetails
import com.etb.weather.comon.mock
import com.etb.weather.comon.whenever
import com.etb.weather.provideSampleResponse
import com.etb.weather.services.providers.cities.CitiesProvider
import com.etb.weather.ui.list.presentation.ListEmpty
import com.etb.weather.ui.list.presentation.ListError
import com.etb.weather.ui.list.presentation.ListLoaded
import com.etb.weather.ui.list.presentation.ListLoading
import com.etb.weather.ui.list.usecase.GetCitiesUseCase
import io.reactivex.Single
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentMatchers.anyString

class GetCityListUseCaseTest {
    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    val citiesSource = mock<CitiesProvider>()

    val useCase by lazy { GetCitiesUseCase(citiesSource) }

    @Test
    fun testGetCityUseCase_getCities_Empty() {
        whenever(citiesSource.getCities(anyString()))
                .thenReturn(Single.just(emptyList()))

        useCase.invoke("")
                .test()
                .assertNoErrors()
                .assertValues(ListLoading, ListEmpty)
    }

    @Test
    fun testGetCityUseCase_getCities_Error() {
        val errorMsg = "Error response"
        val response = Throwable(errorMsg)
        whenever(citiesSource.getCities(anyString()))
                .thenReturn(Single.error(response))


        val expectedError = ErrorDetails("Network Error", errorMsg)
        useCase.invoke("")
                .test()
                .assertNoErrors()
                .assertValues(ListLoading, ListError(expectedError))

    }

    @Test
    fun testGetCityUseCase_getCities_Response() {
        val values = provideSampleResponse()
        val response = arrayListOf(*(values))
        whenever(citiesSource.getCities(anyString()))
                .thenReturn(Single.just(response))

        useCase.invoke("")
                .test()
                .assertNoErrors()
                .assertValues(ListLoading, ListLoaded(response))
    }
}