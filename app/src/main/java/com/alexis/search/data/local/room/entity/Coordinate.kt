package com.alexis.search.data.local.room.entity

import androidx.room.ColumnInfo

data class Coordinate(
    @ColumnInfo(name = "lon") val lon: Double,
    @ColumnInfo(name = "lat") val lat: Double
)

fun Coordinate.toDomain() =
    com.alexis.search.domain.model.Coordinate(lon, lat)