package com.alexis.search.ui.search

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemKey
import com.alexis.search.R
import com.alexis.search.domain.model.City
import com.alexis.search.ui.core.ShowButton
import com.alexis.search.ui.core.ShowSpacer

@Composable
fun SearchScreen(
    modifier: Modifier,
    query: String,
    lazyCity: LazyPagingItems<City>,
    onQueryChange: (String) -> Unit,
    onItemSelected: (Int) -> Unit,
    onFavoriteChange: (Int, Boolean) -> Unit,
    onNavigateToDetail: (Int) -> Unit
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
                            city = item,
                            onItemSelected = {
                                onItemSelected(it)
                            },
                            onFavoriteChange = { cityId, isFavorite ->
                                onFavoriteChange(cityId, isFavorite)
                            },
                            onNavigateToDetail = {
                                onNavigateToDetail(it)
                            }
                        )
                    }
                }
                val isListEmpty = lazyCity.itemCount == 0

                if (isListEmpty) {
                    item {
                        EmptyListContent()
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
            Text(stringResource(id = R.string.search_placeholder))
        },
        value = text,
        onValueChange = { onValueChange(it) },
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = Color(0xFFCCCCCC),
                shape = RoundedCornerShape(25.dp)
            )
            .testTag(stringResource(id = R.string.search_tag)),
        shape = RoundedCornerShape(25.dp),
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = stringResource(id = R.string.search_icon)
            )
        },
        trailingIcon = {
            if (text.isNotEmpty()) {
                Icon(
                    modifier = Modifier.clickable {
                        onValueChange("")
                    },
                    imageVector = Icons.Default.Clear,
                    contentDescription = stringResource(id = R.string.clear)
                )
            }
        },
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
        )
    )
}

@Composable
fun ItemCity(
    city: City,
    onItemSelected: (Int) -> Unit,
    onFavoriteChange: (Int, Boolean) -> Unit,
    onNavigateToDetail: (Int) -> Unit
) {
    Card(
        shape = RoundedCornerShape(10.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier
                    .clickable {
                        onItemSelected(city.id)
                    }
            ) {
                Image(
                    modifier = Modifier.size(50.dp),
                    painter = painterResource(id = R.drawable.maps),
                    contentDescription = stringResource(id = R.string.maps_icon)
                )
                ShowSpacer(10)
                Column {
                    Text(
                        text = "${city.name}-${city.country}",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        fontWeight = FontWeight.Bold,
                    )
                    ShowSpacer(5)
                    Text(
                        text = "Lon: ${city.coordinate.lon}",
                    )
                    ShowSpacer(2)
                    Text(
                        text = "Lat: ${city.coordinate.lat}",
                    )
                }
            }
            Spacer(modifier = Modifier.weight(1f))
            val isFavorite = city.favorite
            AddFloatingButton(
                imageVector = if (isFavorite) Icons.Outlined.Favorite else Icons.Outlined.FavoriteBorder,
                color = if (isFavorite) Color.Red else Color.Black,
                contentDescription = R.string.favorite_icon
            ) {
                onFavoriteChange(city.id, !isFavorite)
            }
        }
        ShowSpacer(2)
        ShowButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .testTag("${stringResource(id = R.string.navigate_detail_city_tag)}-${city.id}"),
            label = stringResource(id = R.string.detail)
        ) {
            onNavigateToDetail(city.id)
        }
    }
}

@Composable
fun EmptyListContent() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(id = R.string.not_found),
            modifier = Modifier
                .padding(16.dp)
        )
    }
}


@Composable
fun AddFloatingButton(
    imageVector: ImageVector,
    color: Color = Color.Black,
    @StringRes contentDescription: Int,
    onIconClickListener: () -> Unit
) {
    FloatingActionButton(onClick = { onIconClickListener() }) {
        Icon(
            imageVector = imageVector,
            contentDescription = stringResource(contentDescription),
            tint = color
        )
    }
}