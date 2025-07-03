package com.alexis.search.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.alexis.search.domain.model.City
import com.alexis.search.ui.core.ShowCircularIndicator
import com.alexis.search.ui.core.ShowSpacer
import com.alexis.search.ui.core.UiState
import com.alexis.search.ui.route.Route

@Composable
fun HomeScreen(
    modifier: Modifier,
    navController: NavHostController,
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val query by homeViewModel.searchQuery.collectAsStateWithLifecycle()
    val cities = homeViewModel.cities.collectAsLazyPagingItems()

    when (val state = homeViewModel.state.collectAsStateWithLifecycle().value) {
        is UiState.Loading -> {
            ShowCircularIndicator()
        }

        is UiState.Success -> {
            ShowHomeScreen(
                modifier = modifier,
                query = query,
                lazyCity = cities,
            ) {
                homeViewModel.searchCity(it)
            }
        }

        is UiState.Failure -> {
            navController.navigate(Route.Failure.createRoute(state.exception.message ?: ""))
        }
    }
}

@Composable
fun ShowHomeScreen(
    modifier: Modifier = Modifier,
    query: String,
    lazyCity: LazyPagingItems<City>,
    onQueryChange: (String) -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        Column(
            modifier = modifier
        ) {
            Search(
                text = query,
                onValueChange = {
                    onQueryChange(it)
                }
            )
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
            ) {
                items(
                    count = lazyCity.itemCount,
                    key = lazyCity.itemKey { it.id }
                ) { index ->
                    val item = lazyCity[index]
                    if (item != null) {
                        ShowSpacer(10)
                        ItemCity(
                            modifier = Modifier,
                            city = item
                        ) {

                        }
                    } else {
                        LoadingItemPlaceholder()
                    }
                }
            }
        }
    }
}

@Composable
fun Search(text: String, onValueChange: (String) -> Unit) {
    TextField(
        placeholder = {
            Text("Search...")
        },
        value = text,
        onValueChange = { onValueChange(it) },
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = Color(0xFFCCCCCC),
                shape = RoundedCornerShape(25.dp)
            ),
        shape = RoundedCornerShape(25.dp),
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search"
            )
        },
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
        )
    )
}

@Composable
fun ItemCity(modifier: Modifier, city: City, onItemSelected: (Int) -> Unit) {
    Card(
        modifier = Modifier
            .clickable {
                onItemSelected(city.id)
            },
        shape = RoundedCornerShape(10.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.LocationOn,
                contentDescription = "Location Icon",
                modifier = Modifier.size(50.dp)
            )
            ShowSpacer(10)
            Column {
                Text(
                    text = "${city.name}-${city.country}",
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.headlineSmall
                )
                ShowSpacer(5)
                Text(
                    text = "Lon: ${city.coordinate.lon}",
                    style = MaterialTheme.typography.bodyMedium
                )
                ShowSpacer(2)
                Text(
                    text = "Lat: ${city.coordinate.lat}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@Composable
fun LoadingItemPlaceholder() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .padding(vertical = 4.dp)
            .background(Color.LightGray.copy(alpha = 0.5f)),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Text(
            text = "List Empty",
        )
    }
}