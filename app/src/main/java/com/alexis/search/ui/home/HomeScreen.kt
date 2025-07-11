package com.alexis.search.ui.home

import android.content.res.Configuration
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import com.alexis.search.R
import com.alexis.search.ui.core.ShowCircularIndicator
import com.alexis.search.ui.core.UiState
import com.alexis.search.ui.landscape.LandscapeScreen
import com.alexis.search.ui.maps.ShowMapScreen
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
                if (idCountry != 0) {
                    ShowMapScreen(
                        modifier = Modifier,
                        idCountry = idCountry,
                        navController = navController,
                        cameraPositionState = cameraPositionState,
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
                            idCountry = it
                        },
                        onFavoriteChange = { cityId, isFavorite ->
                            homeViewModel.updateFavorite(cityId, isFavorite)
                        },
                        onNavigateToDetail = {
                            navController.navigate(Route.Detail.createRoute(it))
                        }
                    )
                }
                if (idCountry != 0) {
                    BackHandler {
                        idCountry = 0
                    }
                }
            }
        }

        is UiState.Failure -> {
            navController.navigate(
                Route.Failure.createRoute(
                    state.exception.message ?: stringResource(id = R.string.exception)
                )
            )
        }
    }
}
