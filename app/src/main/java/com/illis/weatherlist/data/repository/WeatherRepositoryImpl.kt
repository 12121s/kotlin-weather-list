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
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val weatherApiService: WeatherApiService
) : WeatherRepository {

    override fun getWeathers() = flow<UiState<List<WeatherDto?>>> {
        val responseSeoul = weatherApiService.getWeather(ServerConsts.CITY_NAME_SEOUL, ServerConsts.API_KEY)
        val seoul by lazy { responseSeoul.body() }
        if (!responseSeoul.isSuccessful) {
            throw NetworkFailureException("[${responseSeoul.code()}] - ${responseSeoul.raw()}")
        }

        val responseLondon = weatherApiService.getWeather(ServerConsts.CITY_NAME_LONDON, ServerConsts.API_KEY)
        val london by lazy { responseLondon.body() }
        if (!responseLondon.isSuccessful) {
            throw NetworkFailureException("[${responseLondon.code()}] - ${responseLondon.raw()}")
        }

        val responseChicago = weatherApiService.getWeather(ServerConsts.CITY_NAME_CHICAGO, ServerConsts.API_KEY)
        val chicago by lazy { responseChicago.body() }
        if (!responseChicago.isSuccessful) {
            throw NetworkFailureException("[${responseChicago.code()}] - ${responseChicago.raw()}")
        }

        emit(UiState.Success(
            listOf(seoul, london, chicago)
        ))
    }.catch { emit(UiState.Error(it)) }
    .flowOn(Dispatchers.IO)
}