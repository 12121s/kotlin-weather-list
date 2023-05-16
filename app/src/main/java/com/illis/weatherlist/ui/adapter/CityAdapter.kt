package com.illis.weatherlist.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.illis.weatherlist.data.model.WeatherDto
import com.illis.weatherlist.databinding.ItemWeatherCityBinding

class CityAdapter : ListAdapter<WeatherDto, CityAdapter.WeatherViewHolder>(diffCallback) {
    private var inflater: LayoutInflater? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        return WeatherViewHolder(ItemWeatherCityBinding.inflate(
                inflater?: LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<WeatherDto>() {
            override fun areItemsTheSame(oldItem: WeatherDto, newItem: WeatherDto): Boolean {
                return oldItem.city.name == newItem.city.name
            }

            override fun areContentsTheSame(oldItem: WeatherDto, newItem: WeatherDto): Boolean {
                return oldItem == newItem
            }
        }
    }

    inner class WeatherViewHolder(
        private val binding: ItemWeatherCityBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        private val itemAdapter by lazy { WeatherItemAdapter() }

        fun bind(item: WeatherDto) {
            binding.city = item.city.name
            binding.wItemRecyclerView.adapter = itemAdapter
            itemAdapter.submitList(item.list.filter { it.dt_txt.contains("09:00:00") })
        }
    }
}