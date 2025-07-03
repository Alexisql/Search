package com.alexis.search.ui.home

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import com.alexis.search.ui.core.ShowCircularIndicator
import com.alexis.search.ui.core.UiState
import com.alexis.search.ui.lanscape.LandscapeScreen
import com.alexis.search.ui.route.Route
import com.alexis.search.ui.search.SearchScreen
import com.google.maps.android.compose.CameraPositionState

@Composable
fun HomeScreen(
    modifier: Modifier,
    navController: NavHostController,
    cameraPositionState: CameraPositionState,
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
    val query by homeViewModel.searchQuery.collectAsStateWithLifecycle()
    val cities = homeViewModel.cities.collectAsLazyPagingItems()
    var idCountry by rememberSaveable { mutableIntStateOf(0) }

    when (val state = homeViewModel.state.collectAsStateWithLifecycle().value) {
        is UiState.Loading -> {
            ShowCircularIndicator()
        }

        is UiState.Success -> {
            if (isLandscape) {
                LandscapeScreen(
                    modifier = modifier,
                    navController = navController,
                    query = query,
                    lazyCity = cities,
                    cameraPositionState = cameraPositionState,
                    homeViewModel = homeViewModel,
                    idCountry = idCountry,
                    onChangeCountry = {
                        idCountry = it
                    }
                )
            } else {
                SearchScreen(
                    modifier = modifier,
                    query = query,
                    lazyCity = cities,
                    onQueryChange = {
                        homeViewModel.searchCity(it)
                    },
                    onItemSelected = {
                        navController.navigate(Route.Maps.createRoute(it))
                    },
                    onFavoriteChange = { cityId, isFavorite ->
                        homeViewModel.updateFavorite(cityId, isFavorite)
                    }
                )
            }
        }

        is UiState.Failure -> {
            navController.navigate(Route.Failure.createRoute(state.exception.message ?: ""))
        }
    }
}
