package com.illis.weatherlist.domain.repository

import com.illis.weatherlist.data.model.WeatherDto
import com.illis.weatherlist.ui.UiState
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {
    fun getWeathers() : Flow<UiState<List<WeatherDto?>>>
}