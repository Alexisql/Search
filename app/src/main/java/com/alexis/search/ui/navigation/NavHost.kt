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
import com.alexis.search.ui.detail.DetailCityScreen
import com.alexis.search.ui.home.HomeScreen
import com.alexis.search.ui.maps.ShowMapScreen
import com.alexis.search.ui.route.Route
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun Navigation(
    navController: NavHostController,
    innerPadding: PaddingValues
) {
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(
            LatLng(0.0, -0.0),
            15f
        )
    }
    NavHost(
        navController = navController,
        startDestination = Route.Home.route,
        modifier = Modifier.padding(innerPadding)
    ) {
        composable(Route.Home.route) {
            HomeScreen(
                modifier = Modifier,
                navController = navController,
                cameraPositionState = cameraPositionState
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
                idCountry = backStackEntry.arguments?.getInt("idCountry") ?: 0,
                navController = navController,
                cameraPositionState= cameraPositionState,
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
                message = backStackEntry.arguments?.getString("message") ?: ""
            )
        }
        composable(
            route = Route.Detail.route,
            arguments = listOf(
                navArgument("idCountry") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            DetailCityScreen(
                idCountry = backStackEntry.arguments?.getInt("idCountry") ?: 0,
                navController = navController
            )
        }
    }
}
