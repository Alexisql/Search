package com.alexis.search.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.alexis.search.data.local.room.dao.CityDao
import com.alexis.search.data.local.room.entity.CityEntity

@Database(entities = [CityEntity::class], version = 1)
abstract class CityDataBase : RoomDatabase() {
    abstract fun getCityDao(): CityDao
}