package com.illis.weatherlist.ui

sealed class UiState<out T> {
    object Empty: UiState<Nothing>()
    object Loading: UiState<Nothing>()
    data class Success<T>(val data: T): UiState<T>()
    data class Error(val error: Throwable?): UiState<Nothing>()
}

fun <T> UiState<T>.successOrNull(): T? = if (this is UiState.Success<T>) data  else null
fun <T> UiState<T>.throwableOrNull(): Throwable? = if (this is UiState.Error) error else null