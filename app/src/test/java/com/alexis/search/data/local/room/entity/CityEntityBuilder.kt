package com.alexis.search.data.local.room.entity

class CityEntityBuilder {

    private val cities = listOf(
        CityEntity(
            _id = 3688689,
            name = "Bogota",
            country = "CO",
            coord = CoordinateEntityBuilder().build(),
            favorite = true
        ),
        CityEntity(
            _id = 4164166,
            name = "Miami Gardens",
            country = "US",
            coord = CoordinateEntityBuilder().build(),
            favorite = false
        ),
        CityEntity(
            _id = 4164211,
            name = "Miami Shores",
            country = "US",
            coord = CoordinateEntityBuilder().build(),
            favorite = false
        )
    )

    fun build() = cities
}