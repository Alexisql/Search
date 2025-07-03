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
import com.alexis.search.ui.core.ShowErrorScreen
import com.alexis.search.ui.home.HomeScreen
import com.alexis.search.ui.maps.ShowMapScreen
import com.alexis.search.ui.route.Route

@Composable
fun Navigation(
    navController: NavHostController,
    innerPadding: PaddingValues
) {
    NavHost(
        navController = navController,
        startDestination = Route.Home.route,
        modifier = Modifier.padding(innerPadding)
    ) {
        composable(Route.Home.route) {
            HomeScreen(
                modifier = Modifier,
                navController = navController
            )
        }
        composable(
            route = Route.Maps.route,
            arguments = listOf(
                navArgument("idCountry") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            ShowMapScreen(
                modifier = Modifier,
                idCountry = backStackEntry.arguments?.getInt("idCountry") ?: 0
            )
        }
        composable(
            route = Route.Failure.route,
            arguments = listOf(
                navArgument("message") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            ShowErrorScreen(
                modifier = Modifier,
                message = backStackEntry.arguments?.getString("message") ?: "",
                navController = navController
            )
        }
    }
}
