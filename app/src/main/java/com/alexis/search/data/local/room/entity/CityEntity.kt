package com.alexis.search.data.local.room.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "City")
data class CityEntity(
    @PrimaryKey
    @ColumnInfo(name = "id") val _id: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "country") val country: String,
    @Embedded(prefix = "coordinate") val coord: CoordinateEntity,
    @ColumnInfo(name = "favorite") val favorite: Boolean = false
)

fun CityEntity.toDomain() =
    com.alexis.search.domain.model.City(_id, name, country, coord.toDomain(), favorite)