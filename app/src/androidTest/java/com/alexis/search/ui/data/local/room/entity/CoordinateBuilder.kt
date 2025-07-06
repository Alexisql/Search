package com.alexis.search.ui.data.local.room.entity

import com.alexis.search.data.local.room.entity.Coordinate

class CoordinateBuilder {

    private val coordinate = Coordinate(-74.081749, 4.60971)

    fun build() = coordinate

}

fun Coordinate.toDomain() = com.alexis.search.domain.model.Coordinate(
    lat = this.lat,
    lon = this.lon
)