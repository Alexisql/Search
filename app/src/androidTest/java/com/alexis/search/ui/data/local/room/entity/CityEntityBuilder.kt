package com.alexis.search.ui.data.local.room.entity

import com.alexis.search.data.local.room.entity.CityEntity
import com.alexis.search.domain.model.City

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

fun CityEntityBuilder.toDomain() = CityEntityBuilder().build().map {
    City(
        id = it._id,
        name = it.name,
        country = it.country,
        coordinate = it.coord.toDomain(),
        favorite = it.favorite
    )
}