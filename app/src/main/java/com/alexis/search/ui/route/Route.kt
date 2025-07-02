package com.alexis.search.ui.route

sealed class Route(val route: String) {
    data object Home : Route("Home")
    data object Maps : Route("maps/{idCountry}") {
        fun createRoute(idCountry: Int) = "maps/$idCountry"
    }
}