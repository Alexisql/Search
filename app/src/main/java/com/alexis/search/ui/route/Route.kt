package com.alexis.search.ui.route

sealed class Route(val route: String) {
    data object Home : Route("Home")
    data object Failure : Route("failure/{message}") {
        fun createRoute(message: String) = "failure/$message"
    }
    data object Detail : Route("detail/{idCountry}") {
        fun createRoute(idCountry: Int) = "detail/$idCountry"
    }
}