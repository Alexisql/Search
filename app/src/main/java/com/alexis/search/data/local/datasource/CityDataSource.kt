package com.alexis.search.data.local.datasource

import android.content.Context
import android.util.Log
import com.alexis.search.data.local.room.entity.CityEntity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.IOException
import java.io.InputStreamReader
import javax.inject.Inject

class CityDataSource @Inject constructor(
    @ApplicationContext private val context: Context,
    private val gson: Gson
) {

    fun getCitiesFromJsonAsset(): List<CityEntity> {
        var reader: InputStreamReader? = null
        try {
            val inputStream = context.assets.open("cities.json")
            reader = InputStreamReader(inputStream)
            val cityListType = object : TypeToken<List<CityEntity>>() {}.type
            return gson.fromJson(reader, cityListType)
        } catch (exception: IOException) {
            Log.e("CityDataSource", "Error reading JSON file", exception)
            throw exception
        } finally {
            reader?.close()
        }
    }
}