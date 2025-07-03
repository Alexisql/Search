package com.alexis.search.ui.maps

import android.util.Log
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
            Log.e("MapScreen", "Init Loading")
        }

        is UiState.Success -> {
            Log.e("MapScreen", "Init Success")
            val coordinate = state.data.coordinate
            val newCoordinates = LatLng(coordinate.lat, coordinate.lon)
            LaunchedEffect(idCountry) {
                Log.e("MapScreen", "LaunchedEffect")
                cameraPositionState.animate(
                    update = CameraUpdateFactory.newLatLngZoom(newCoordinates, 15f)
                )
            }
            ShowGoogleMap(
                modifier = modifier,
                cameraPositionState = cameraPositionState,
                coordinates = newCoordinates
            )
        }

        is UiState.Failure -> {
            Log.e("MapScreen", "Init Failure")
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
    Log.e("MapScreen", "GoogleMap")
    Log.e("MapScreen", "Coordinates: Lat: ${coordinates.latitude} - Lon: ${coordinates.longitude}")
    GoogleMap(
        modifier = modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState
    ) {
        Marker(
            state = MarkerState(position = coordinates),
        )
    }
}