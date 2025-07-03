package com.alexis.search.domain.model

data class City(
    val id: Int,
    val name: String,
    val country: String,
    val coordinate: Coordinate,
    val favorite: Boolean
)

