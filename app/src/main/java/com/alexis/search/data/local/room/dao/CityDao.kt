package com.alexis.search.data.local.room.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.alexis.search.data.local.room.entity.CityEntity

@Dao
interface CityDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(cities: List<CityEntity>)

    @Query("SELECT COUNT(*) FROM City")
    suspend fun getCount(): Int

    @Query("SELECT * FROM City WHERE name LIKE :cityName || '%' ORDER BY name")
    fun searchCities(cityName: String): PagingSource<Int, CityEntity>

    @Query("UPDATE City SET favorite = :isFavorite WHERE id = :cityId")
    suspend fun updateFavorite(cityId: Int, isFavorite: Boolean): Int

    @Query("SELECT * FROM City WHERE favorite = 1 ORDER BY name")
    fun getFavoriteCities(): PagingSource<Int, CityEntity>

    @Query("SELECT * FROM City WHERE id = :cityId")
    suspend fun getCityById(cityId: Int): CityEntity?

    @Query("SELECT * FROM City")
    fun getAllCities(): PagingSource<Int, CityEntity>
}