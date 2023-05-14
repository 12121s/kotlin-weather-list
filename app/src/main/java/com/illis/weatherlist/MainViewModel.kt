package com.illis.weatherlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.illis.weatherlist.domain.usecase.GetWeatherUseCase
import com.illis.weatherlist.ui.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getWeatherUseCase: GetWeatherUseCase
) : ViewModel() {


    val weatherList = getWeatherUseCase.getWeathers()
        .stateIn(
            initialValue = UiState.Loading,
            started = SharingStarted.Lazily,
            scope = viewModelScope
        )
}