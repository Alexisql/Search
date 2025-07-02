package com.alexis.search.ui.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.alexis.search.ui.home.ShowHomeScreen
import com.alexis.search.ui.maps.ShowMapScreen
import com.alexis.search.ui.route.Route

@Composable
fun Navigation(navController: NavHostController, innerPadding: PaddingValues) {
    NavHost(
        navController = navController,
        startDestination = Route.Home.route,
        modifier = Modifier.padding(innerPadding)
    ) {
        composable(Route.Home.route) {
            ShowHomeScreen(
                modifier = Modifier,
                navController = navController
            )
        }
        composable(
            Route.Maps.route,
            arguments = listOf(
                navArgument("idCountry") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            ShowMapScreen(
                modifier = Modifier,
                idCountry = backStackEntry.arguments?.getInt("idCountry") ?: 0
            )
        }
    }
}
