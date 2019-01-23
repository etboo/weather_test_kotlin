package com.etb.weather.ui.forecast.di

import com.etb.weather.BuildConfig
import com.etb.weather.common.UseCase
import com.etb.weather.di.scopes.FragmentScope
import com.etb.weather.services.providers.forecast.ForecastNetwork
import com.etb.weather.services.providers.forecast.ForecastProvider
import com.etb.weather.services.providers.forecast.WeatherApi
import com.etb.weather.ui.forecast.presentation.ForecastState
import com.etb.weather.ui.forecast.usecase.GetForecastUseCase
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import io.reactivex.Observable
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named

const val FORECAST_USE_CASE = "FORECAST_USE_CASE"

@Module
class ForecastServicesModule {

    @FragmentScope
    @Provides
    fun provideRetrofit(gson: Gson, client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
                .baseUrl(BuildConfig.WEATHER_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
    }

    @Provides
    @FragmentScope
    fun provideApi(retrofit: Retrofit): WeatherApi {
        return retrofit.create(WeatherApi::class.java)
    }

    @Provides
    @FragmentScope
    fun provideCitiesProvider(api: WeatherApi): ForecastProvider {
        return ForecastNetwork( api)
    }

    @Provides
    @FragmentScope
    @Named(FORECAST_USE_CASE)
    fun provideForecastUseCase(provider: ForecastProvider): UseCase<@JvmWildcard String, @JvmWildcard Observable<@JvmWildcard ForecastState>> {
        return GetForecastUseCase(provider)
    }

}
