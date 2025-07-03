package com.alexis.search.ui.maps

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexis.search.di.DispatcherIO
import com.alexis.search.domain.model.City
import com.alexis.search.domain.repository.ICityRepository
import com.alexis.search.ui.core.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val cityRepository: ICityRepository,
    @DispatcherIO private val dispatcherIO: CoroutineDispatcher
) : ViewModel() {

    private val _state = MutableStateFlow<UiState<City>>(UiState.Loading)
    val state: StateFlow<UiState<City>> = _state

    fun getCityById(cityId: Int) {
        viewModelScope.launch(dispatcherIO) {
            cityRepository.getCityById(cityId)
                .onSuccess { _state.value = UiState.Success(it) }
                .onFailure { _state.value = UiState.Failure(it) }
        }
    }

}