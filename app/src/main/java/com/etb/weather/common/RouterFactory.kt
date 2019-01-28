package com.etb.weather.common

import android.support.v4.app.Fragment
import com.etb.weather.ui.Action

interface RouterFactory {
    fun <F : Fragment> from(fragment: F): Router<F>

    fun initialRoute(): Route<Unit>
}

interface Router<F: Fragment>{
    fun <M> act(action: Action<F, M>): Route<M>
}
