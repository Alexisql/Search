package com.alexis.search.ui.landscape

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.paging.compose.LazyPagingItems
import com.alexis.search.domain.model.City
import com.alexis.search.ui.home.HomeViewModel
import com.alexis.search.ui.maps.ShowMapScreen
import com.alexis.search.ui.route.Route
import com.alexis.search.ui.search.SearchScreen
import com.google.maps.android.compose.CameraPositionState

@Composable
fun LandscapeScreen(
    navController: NavHostController,
    query: String,
    lazyCity: LazyPagingItems<City>,
    cameraPositionState: CameraPositionState,
    homeViewModel: HomeViewModel,
    idCountry: Int,
    onChangeCountry: (Int) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(modifier = Modifier.weight(0.5f)) {
            SearchScreen(
                modifier = Modifier,
                query = query,
                lazyCity = lazyCity,
                onQueryChange = {
                    homeViewModel.searchCity(it)
                },
                onItemSelected = {
                    onChangeCountry(it)
                },
                onFavoriteChange = { cityId, isFavorite ->
                    homeViewModel.updateFavorite(cityId, isFavorite)
                },
                onNavigateToDetail = {
                    navController.navigate(Route.Detail.createRoute(it))
                }
            )
        }
        Box(modifier = Modifier.weight(0.5f)) {
            ShowMapScreen(
                modifier = Modifier,
                idCountry = idCountry,
                cameraPositionState = cameraPositionState,
                navController = navController
            )
        }
    }
}