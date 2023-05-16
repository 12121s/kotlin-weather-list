package com.illis.weatherlist.domain.usecase

import com.illis.weatherlist.data.model.WeatherDto
import com.illis.weatherlist.domain.repository.WeatherRepository
import com.illis.weatherlist.ui.UiState
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class GetWeatherUseCase @Inject constructor(
    private val weatherRepository: WeatherRepository
) {
    fun getWeathers(): Flow<UiState<List<WeatherDto?>>> {
        return weatherRepository.getWeathers()
    }
}