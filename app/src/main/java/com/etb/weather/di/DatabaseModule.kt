package com.etb.weather.di

import android.content.Context
import com.etb.weather.BuildConfig
import dagger.Module
import dagger.Provides
import io.realm.Realm
import io.realm.RealmConfiguration
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun providesRealm(context: Context): Realm {
        Realm.init(context)
        val builder = RealmConfiguration.Builder()
                .name("com.etb.weathertest")
                .schemaVersion(1)
//                .migration()
        if (BuildConfig.DEBUG) {
            builder.deleteRealmIfMigrationNeeded()
        }
        Realm.setDefaultConfiguration(builder.build())

        return Realm.getDefaultInstance()
    }

}
