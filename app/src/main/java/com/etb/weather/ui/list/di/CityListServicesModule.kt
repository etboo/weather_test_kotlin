package com.etb.weather.ui.list.di

import com.etb.weather.BuildConfig
import com.etb.weather.di.scopes.FragmentScope
import com.etb.weather.services.providers.cities.CitiesApi
import com.etb.weather.services.providers.cities.CitiesNetwork
import com.etb.weather.services.providers.cities.CitiesProvider
import com.etb.weather.ui.list.presentation.ListState
import com.etb.weather.ui.list.usecase.GetCitiesUseCase
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import io.reactivex.Observable
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named


const val CITIES_USE_CASE = "CITIES_USE_CASE"

@Module
class CityListServicesModule {

    @FragmentScope
    @Provides
    fun provideRetrofit(gson: Gson, client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
                .baseUrl(BuildConfig.PLACES_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
    }

    @Provides
    @FragmentScope
    fun provideApi(retrofit: Retrofit): CitiesApi {
        return retrofit.create(CitiesApi::class.java)
    }

    @Provides
    @FragmentScope
    fun provideCitiesProvider(api: CitiesApi): CitiesProvider {
        return CitiesNetwork(BuildConfig.PLACES_API_KEY, api)
    }

    @Provides
    @FragmentScope
    @Named(CITIES_USE_CASE)
    fun provideCitiesUseCase(provider: CitiesProvider): Function1<@JvmWildcard String, @JvmWildcard Observable<@JvmWildcard ListState>> {
        return GetCitiesUseCase(provider)
    }

}