package com.alexis.search.ui.core

sealed class UiState<out R> {
    data object Loading : UiState<Nothing>()
    data class Success<out T>(val data: T) : UiState<T>()
    data class Failure(val exception: Throwable) : UiState<Nothing>()
}