package com.alexis.search.data.local.room.repository

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.alexis.search.data.local.datasource.CityDataSource
import com.alexis.search.data.local.room.dao.CityDao
import com.alexis.search.data.local.room.entity.toDomain
import com.alexis.search.domain.model.City
import com.alexis.search.domain.repository.ICityRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CityRepositoryImpl @Inject constructor(
    private val cityDao: CityDao,
    private val cityDataSource: CityDataSource,
) : ICityRepository {

    override suspend fun insertAll(): Result<Unit> {
        return try {
            val count = cityDao.getCount()
            if (count == 0) {
                val cities = cityDataSource.getCitiesFromJsonAsset()
                cities.chunked(1000).forEach {
                    cityDao.insertAll(it)
                }
            }
            Result.success(Unit)
        } catch (exception: Exception) {
            Log.e("CityRepositoryImpl", "Error inserting cities", exception)
            Result.failure(exception)
        }
    }

    override fun searchCity(cityName: String): Flow<PagingData<City>> {
        return Pager(
            config = PagingConfig(pageSize = 50),
            pagingSourceFactory = { cityDao.searchCities(cityName) }
        ).flow.map { pagingData ->
            pagingData.map { it.toDomain() }
        }
    }
}