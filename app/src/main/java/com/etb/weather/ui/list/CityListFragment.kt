package com.etb.weather.ui.list

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.view.View
import android.view.ViewGroup
import com.etb.weather.R
import com.etb.weather.common.BaseFragment
import com.etb.weather.ui.list.presentation.*
import com.jakewharton.rxbinding2.widget.textChanges
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import kotlinx.android.synthetic.main.fragment_list.view.*
import kotlinx.android.synthetic.main.layout_list_error.view.*
import javax.inject.Inject

class CityListFragment : BaseFragment<CityListViewModel>() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override val layoutId = R.layout.fragment_list

    private var citiesAdapter: CitiesAdapter? = null

    override fun provideViewModel() = ViewModelProviders.of(this, viewModelFactory)[CityListViewModel::class.java]

    override fun initUI(view: View) {
        citiesAdapter = CitiesAdapter()
        view.citiesListRv.adapter = citiesAdapter
    }

    override fun bind(view: View, vm: CityListViewModel) = listOf<Disposable>(
            //vm -> view
            vm.state.subscribe(view.citiesLayoutSwitcher.switchViews()),
            vm.listItems.subscribe { citiesAdapter?.cities = it },
            vm.errorDetails.subscribe {
                view.errorTitleTv.text = it.name
                view.errorDescTv.text = it.desc
            },
            //view -> vm
            view.citySearchEt.textChanges()
                    .map{ it.toString() }
                    .subscribe(vm.searchTextChanged)

    )

    companion object {
        fun newInstance(): CityListFragment {
            return CityListFragment()
        }
    }

}

private fun ViewGroup.switchViews() = Consumer<ListState> { state ->
    (0 until this.childCount)
            .map { this.getChildAt(it) }
            .forEach { it.visibility = View.GONE }

    when (state) {
        is Tutorial -> this.citiesListTutorial
        is ListLoading -> this.citiesListLoading
        is ListLoaded -> this.citiesListRv
        is ListEmpty -> this.citiesListEmpty
        is ListError -> this.citiesListError
    }.visibility = View.VISIBLE
}
