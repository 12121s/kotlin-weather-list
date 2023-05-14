package com.illis.weatherlist.data.repository

import com.illis.weatherlist.const.ServerConsts
import com.illis.weatherlist.data.model.WeatherDto
import com.illis.weatherlist.data.source.remote.EmptyBodyException
import com.illis.weatherlist.data.source.remote.NetworkFailureException
import com.illis.weatherlist.data.source.remote.api.WeatherApiService
import com.illis.weatherlist.domain.repository.WeatherRepository
import com.illis.weatherlist.ui.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val weatherApiService: WeatherApiService
) : WeatherRepository {

    override fun getSeoulWeathers() = flow<UiState<WeatherDto>> {
        val response = weatherApiService.getWeather(ServerConsts.CITY_NAME_SEOUL, ServerConsts.API_KEY)
        if (response.isSuccessful) {
            response.body()?.also { data ->
                emit(UiState.Success(data))
            }
        } else {
            throw NetworkFailureException("[${response.code()}] - ${response.raw()}")
        }
    }.catch { emit(UiState.Error(it)) }
    .flowOn(Dispatchers.IO)
}