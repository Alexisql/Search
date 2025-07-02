package com.alexis.search.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexis.search.di.DispatcherDefault
import com.alexis.search.domain.repository.ICityRepository
import com.alexis.search.ui.core.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val cityRepository: ICityRepository,
    @DispatcherDefault private val dispatcherDefault: CoroutineDispatcher
) : ViewModel() {

    private val _state = MutableStateFlow<UiState<Unit>>(UiState.Loading)
    val state: StateFlow<UiState<Unit>> = _state

    init {
        initDatabase()
    }

    private fun initDatabase() {
        viewModelScope.launch(dispatcherDefault) {
            cityRepository.insertAll()
                .onSuccess { _state.value = UiState.Success(Unit) }
                .onFailure { _state.value = UiState.Failure(it) }
        }
    }
}