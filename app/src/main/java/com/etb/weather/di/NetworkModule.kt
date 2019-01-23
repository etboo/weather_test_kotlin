package com.etb.weather.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import javax.inject.Singleton


@Module
class NetworkModule {

    @Singleton
    @Provides
    fun providesGson(): Gson {
        return GsonBuilder()
                .create()
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient
                .Builder()
                .build()
    }

}
