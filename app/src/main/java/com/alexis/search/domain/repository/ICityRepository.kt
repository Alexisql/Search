package com.alexis.search.domain.repository

import androidx.paging.PagingData
import com.alexis.search.domain.model.City
import kotlinx.coroutines.flow.Flow

interface ICityRepository {
    suspend fun insertAll(): Result<Unit>
    fun searchCity(cityName: String): Flow<PagingData<City>>
    suspend fun updateFavorite(cityId: Int, isFavorite: Boolean): Result<Unit>
    fun getFavoriteCities(): Flow<PagingData<City>>
    suspend fun getCityById(cityId: Int): Result<City>
}