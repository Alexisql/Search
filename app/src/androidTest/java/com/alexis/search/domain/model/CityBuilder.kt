package com.alexis.search.domain.model

class CityBuilder {
    private val cities = listOf(
        City(
            id = 3688689,
            name = "Bogota",
            country = "CO",
            coordinate = CoordinateBuilder().build(),
            favorite = true
        ),
        City(
            id = 4164166,
            name = "Miami Gardens",
            country = "US",
            coordinate = CoordinateBuilder().build(),
            favorite = false
        ),
        City(
            id = 4164211,
            name = "Miami Shores",
            country = "US",
            coordinate = CoordinateBuilder().build(),
            favorite = false
        )
    )

    fun build() = cities
}