package com.etb.weather.common

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.etb.weather.ui.MainActivity
import com.jakewharton.rxrelay2.PublishRelay
import dagger.android.support.AndroidSupportInjection
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer

abstract class BaseFragment<VM : BaseViewModel> : Fragment() {

    val backPress = PublishRelay.create<Unit>()

    private var disposable: Disposable? = null

    protected var viewModel: VM? = null

    abstract val layoutId: Int

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(layoutId, container, false).also {
            initUI(it)

            disposable = CompositeDisposable().also { disposable ->
                (activity as MainActivity).backPressed
                        .subscribe(backPress)
                        .disposeWith(disposable)
                bind(it, viewModel!!).forEach { disposable.add(it) }
            }

        }
    }

    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        viewModel = provideViewModel()
        super.onCreate(savedInstanceState)
    }

    abstract fun provideViewModel(): VM

    override fun onDetach() {
        super.onDetach()
    }

    override fun onDestroyView() {
        disposable?.dispose()
        super.onDestroyView()
    }

    open fun bind(view: View, vm: VM) = emptyList<Disposable>()

    open fun initUI(view: View) {}

    protected fun ViewGroup.switchViews() = Consumer<Int> { visibleRes ->
        (0 until this.childCount)
                .map { this.getChildAt(it) }
                .forEach { it.visibility = View.GONE }

        findViewById<View>(visibleRes).visibility = View.VISIBLE
    }

}
