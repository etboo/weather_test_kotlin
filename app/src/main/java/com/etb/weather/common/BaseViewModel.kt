package com.etb.weather.common

import android.arch.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

open class BaseViewModel: ViewModel() {

    protected val disposable = CompositeDisposable()

    override fun onCleared() {
        disposable.dispose()
    }
}