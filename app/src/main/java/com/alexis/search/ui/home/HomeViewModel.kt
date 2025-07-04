package com.alexis.search.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.alexis.search.di.DispatcherIO
import com.alexis.search.domain.model.City
import com.alexis.search.domain.repository.ICityRepository
import com.alexis.search.ui.core.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val cityRepository: ICityRepository,
    @DispatcherIO private val dispatcherIO: CoroutineDispatcher
) : ViewModel() {

    private val _state = MutableStateFlow<UiState<Unit>>(UiState.Loading)
    val state: StateFlow<UiState<Unit>> = _state

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    init {
        initDatabase()
    }

    private fun initDatabase() {
        viewModelScope.launch {
            cityRepository.insertAll()
                .onSuccess { _state.value = UiState.Success(Unit) }
                .onFailure { _state.value = UiState.Failure(it) }
        }
    }

    val cities: Flow<PagingData<City>> = _searchQuery
        .debounce(250)
        .flatMapLatest { query ->
            if (query.isBlank()) {
                cityRepository.getFavoriteCities()
            } else {
                cityRepository.searchCity(query)
            }
        }
        .catch {
            emit(PagingData.empty())
        }
        .flowOn(dispatcherIO)
        .cachedIn(viewModelScope)

    fun searchCity(query: String) {
        _searchQuery.value = query
    }

    fun updateFavorite(cityId: Int, isFavorite: Boolean) {
        viewModelScope.launch(dispatcherIO) {
            cityRepository.updateFavorite(cityId, isFavorite)
                .onSuccess {
                    Log.i("HomeViewModel", "Favorite status updated successfully")
                }
                .onFailure {
                    Log.e("HomeViewModel", "Error updating favorite status", it)
                }
        }
    }

}