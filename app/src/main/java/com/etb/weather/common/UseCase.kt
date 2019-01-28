package com.etb.weather.common

import io.reactivex.Observable

typealias UseCase<I, O> = (I) -> Observable<O>

