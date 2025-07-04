package com.alexis.search.ui.data.local.room.entity

import com.alexis.search.data.local.room.entity.CityEntity

class CityEntityBuilder {

    private val citiesEntity = listOf(
        CityEntity(
            _id = 3688689,
            name = "Bogota",
            country = "CO",
            coord = CoordinateBuilder().build(),
            favorite = true
        ),
        CityEntity(
            _id = 4164166,
            name = "Miami Gardens",
            country = "US",
            coord = CoordinateBuilder().build(),
            favorite = false
        ),
        CityEntity(
            _id = 4164211,
            name = "Miami Shores",
            country = "US",
            coord = CoordinateBuilder().build(),
            favorite = false
        ),
    )

    fun build() = citiesEntity
}