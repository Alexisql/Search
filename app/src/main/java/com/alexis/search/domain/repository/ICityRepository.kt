package com.alexis.search.domain.repository

import androidx.paging.PagingData
import com.alexis.search.domain.model.City
import kotlinx.coroutines.flow.Flow

interface ICityRepository {
    suspend fun insertAll():Result<Unit>
    fun searchCity(cityName: String): Flow<PagingData<City>>
}