package com.alexis.search.ui.maps

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.alexis.search.ui.core.ShowCircularIndicator
import com.alexis.search.ui.core.UiState
import com.alexis.search.ui.route.Route
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState

@Composable
fun ShowMapScreen(
    modifier: Modifier,
    idCountry: Int,
    navController: NavHostController,
    cameraPositionState: CameraPositionState,
    mapViewModel: MapViewModel = hiltViewModel()
) {
    LaunchedEffect(idCountry) {
        mapViewModel.getCityById(idCountry)
    }

    when (val state = mapViewModel.state.collectAsStateWithLifecycle().value) {
        is UiState.Loading -> {
            ShowCircularIndicator()
        }

        is UiState.Success -> {
            val coordinate = state.data.coordinate
            val newCoordinates = LatLng(coordinate.lat, coordinate.lon)
            ShowGoogleMap(
                modifier = modifier,
                cameraPositionState = cameraPositionState,
                coordinates = newCoordinates
            )
        }

        is UiState.Failure -> {
            navController.navigate(Route.Failure.createRoute(state.exception.message ?: ""))
        }
    }
}

@Composable
fun ShowGoogleMap(
    modifier: Modifier,
    cameraPositionState: CameraPositionState,
    coordinates: LatLng
) {
    LaunchedEffect(coordinates) {
        cameraPositionState.animate(
            update = CameraUpdateFactory.newLatLngZoom(coordinates, 15f),
            durationMs = 1000
        )
    }
    GoogleMap(
        modifier = modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState
    ) {
        Marker(
            state = MarkerState(position = coordinates),
        )
    }
}