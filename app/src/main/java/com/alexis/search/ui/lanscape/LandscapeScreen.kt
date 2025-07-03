package com.alexis.search.ui.lanscape

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.paging.compose.LazyPagingItems
import com.alexis.search.domain.model.City
import com.alexis.search.ui.home.HomeViewModel
import com.alexis.search.ui.maps.ShowMapScreen
import com.alexis.search.ui.search.SearchScreen

@Composable
fun LandscapeScreen(
    modifier: Modifier,
    query: String,
    lazyCity: LazyPagingItems<City>,
    homeViewModel: HomeViewModel
) {
    Row(
        modifier = modifier.fillMaxSize()
    ) {
        Column(modifier = modifier.weight(0.5f)) {
            SearchScreen(
                modifier = modifier,
                query = query,
                lazyCity = lazyCity,
            ) {
                homeViewModel.searchCity(it)
            }
        }
        Box(modifier = modifier.weight(0.5f)) {
            ShowMapScreen(
                modifier = modifier,
                idCountry = 0
            )
        }
    }
}