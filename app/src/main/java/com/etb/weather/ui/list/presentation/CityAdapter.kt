package com.etb.weather.ui.list.presentation

import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.etb.weather.R
import com.jakewharton.rxrelay2.PublishRelay
import kotlinx.android.synthetic.main.item_city.view.*

class CitiesAdapter : RecyclerView.Adapter<CitiesAdapter.CityViewHolder>() {

    val itemClicks = PublishRelay.create<Int>()

    var cities: List<String> = listOf()
        set(value) {
            DiffUtil.calculateDiff(object : DiffUtil.Callback() {

                override fun getOldListSize() = field.size

                override fun getNewListSize() = value.size

                override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    return field[oldItemPosition] == value[newItemPosition]
                }

                override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    return field[oldItemPosition] == value[newItemPosition]
                }

            }).apply { dispatchUpdatesTo(this@CitiesAdapter) }

            field = value
        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_city, parent, false)
        return CityViewHolder(view)
    }

    override fun getItemCount() = cities.size

    override fun onBindViewHolder(holder: CityViewHolder?, position: Int) {
        holder?.bind(cities[position], position)
    }

    inner class CityViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: String, position: Int) {
            itemView.apply {
                setOnClickListener { itemClicks.accept(position) }
                itemCityName.text = item
            }
        }
    }
}

