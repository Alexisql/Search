package com.alexis.search.data.local.room.entity

import androidx.room.ColumnInfo

data class CoordinateEntity(
    @ColumnInfo(name = "lon") val lon: Double,
    @ColumnInfo(name = "lat") val lat: Double
)

fun CoordinateEntity.toDomain() =
    com.alexis.search.domain.model.Coordinate(lon, lat)