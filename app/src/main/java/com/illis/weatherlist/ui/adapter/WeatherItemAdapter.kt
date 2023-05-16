package com.illis.weatherlist.ui.adapter

import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.illis.weatherlist.R
import com.illis.weatherlist.data.model.WeatherDto
import com.illis.weatherlist.databinding.ItemWeatherContentBinding
import com.illis.weatherlist.util.DateUtil

class WeatherItemAdapter : ListAdapter<WeatherDto.WeatherInfo, WeatherItemAdapter.WeatherViewHolder>(diffCallback) {
    private var inflater: LayoutInflater? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        return WeatherViewHolder(ItemWeatherContentBinding.inflate(
                inflater?: LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<WeatherDto.WeatherInfo>() {
            override fun areItemsTheSame(oldItem: WeatherDto.WeatherInfo, newItem: WeatherDto.WeatherInfo): Boolean {
                return oldItem.dt == newItem.dt
            }

            override fun areContentsTheSame(oldItem: WeatherDto.WeatherInfo, newItem: WeatherDto.WeatherInfo): Boolean {
                return oldItem == newItem
            }
        }
    }

    inner class WeatherViewHolder(
        private val binding: ItemWeatherContentBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: WeatherDto.WeatherInfo) {
            val timeMillis : Long = DateUtil.convertPrintStringDate(item.dt_txt).time

            val customDate = if(DateUtils.isToday(timeMillis)) "Today"
            else if (DateUtils.isToday(timeMillis - DateUtils.DAY_IN_MILLIS)) "Tomorrow"
            else DateUtil.convertStringToDateString(item.dt_txt)

            binding.dateText.text = customDate
            binding.weatherText.text = item.weather[0].main
            Glide.with(binding.root.context)
                .load(String.format(binding.root.context.getString(R.string.img_url), item.weather[0].icon))
                .into(binding.weatherIcon)
            binding.maxTemp.text = String.format(binding.root.context.getString(R.string.max_temp), item.main.temp_max)
            binding.minTemp.text = String.format(binding.root.context.getString(R.string.min_temp), item.main.temp_min)
        }
    }
}