package com.etb.weather.di

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.etb.weather.di.scopes.FragmentScope
import dagger.MapKey
import javax.inject.Inject
import javax.inject.Provider
import kotlin.reflect.KClass

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@MapKey
internal annotation class ViewModelKey(val value: KClass<out ViewModel>)

@Suppress("UNCHECKED_CAST")
@FragmentScope
class DaggerViewModelFactory
@Inject constructor(
        private val creators: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val creator = creators[modelClass]
                ?: creators.asIterable().firstOrNull { modelClass.isAssignableFrom(it.key) }?.value
                ?: throw IllegalArgumentException("unknown model class $modelClass")

        return try {
            creator.get() as T
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }
}
