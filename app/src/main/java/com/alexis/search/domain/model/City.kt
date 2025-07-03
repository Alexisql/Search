package com.alexis.search.domain.model

data class City(
    val id: Int,
    val name: String,
    val country: String,
    val coordinate: Coordinate,
    val favorite: Boolean
){
    companion object{
        fun empty() = City(
            id = 0,
            name = "",
            country = "",
            coordinate = Coordinate(0.0, 0.0),
            favorite = false
        )
    }
}

