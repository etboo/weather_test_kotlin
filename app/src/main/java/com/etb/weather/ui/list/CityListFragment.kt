package com.etb.weather.ui.list

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.view.View
import android.view.ViewGroup
import com.etb.weather.R
import com.etb.weather.common.BaseFragment
import com.etb.weather.ui.BackFromExitDialog
import com.etb.weather.ui.BackFromList
import com.etb.weather.ui.ForecastSelected
import com.etb.weather.ui.MainActivity
import com.etb.weather.ui.list.presentation.CitiesAdapter
import com.etb.weather.ui.list.presentation.StatePresenter
import com.jakewharton.rxbinding2.widget.textChanges
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import kotlinx.android.synthetic.main.fragment_list.view.*
import kotlinx.android.synthetic.main.layout_list_error.view.*
import javax.inject.Inject

class CityListFragment : BaseFragment<CityListViewModel>() {

    private val router by lazy { (activity as MainActivity).getRouterFor(this) }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var statePresenter: StatePresenter

    override val layoutId = R.layout.fragment_list

    private lateinit var citiesAdapter: CitiesAdapter

    override fun provideViewModel() = ViewModelProviders.of(this, viewModelFactory)[CityListViewModel::class.java]

    override fun initUI(view: View) {
        citiesAdapter = CitiesAdapter()
        view.citiesListRv.adapter = citiesAdapter
    }

    override fun bind(view: View, vm: CityListViewModel) = listOf<Disposable>(
            //vm -> view
            vm.state.map(statePresenter)
                    .subscribe(view.citiesLayoutSwitcher.switchViews()),
            vm.listItems
                    .subscribe { citiesAdapter?.cities = it },
            vm.errorDetails
                    .subscribe {
                        view.errorTitleTv.text = it.name
                        view.errorDescTv.text = it.desc
                    },
            vm.citySelected
                    .subscribe(router.act(ForecastSelected)),
            vm.showExitToast
                    .subscribe(router.act(BackFromList)),
            vm.quitApp
                    .subscribe(router.act(BackFromExitDialog)),
            //view -> vm
            view.citySearchEt.textChanges()
                    .map { it.toString() }
                    .subscribe(vm.searchTextChanged),
            backPress.subscribe(vm.backPress),
            citiesAdapter.itemClicks
                    .subscribe(vm.listItemClick)
    )

    companion object {
        fun newInstance(): CityListFragment {
            return CityListFragment()
        }
    }

}

private fun ViewGroup.switchViews() = Consumer<Int> { visibleRes ->
    (0 until this.childCount)
            .map { this.getChildAt(it) }
            .forEach { it.visibility = View.GONE }

    findViewById<View>(visibleRes).visibility = View.VISIBLE
}
