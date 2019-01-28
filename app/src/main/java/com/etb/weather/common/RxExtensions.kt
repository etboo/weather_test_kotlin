package com.etb.weather.common

import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

fun Disposable.disposeWith(compositeDisposable: CompositeDisposable): Disposable {
    compositeDisposable.add(this)
    return this
}

inline fun <I, reified O> Observable<I>.whenIs(clazz: Class<O>): Observable<O> {
    return this.switchMap {
        when(it) {
            is O -> Observable.just<O>(it)
            else -> Observable.empty<O>()
        }
    }
}
