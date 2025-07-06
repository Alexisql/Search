package com.alexis.search.ui.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.navigation.NavHostController
import androidx.paging.PagingData
import com.alexis.search.domain.model.City
import com.alexis.search.ui.core.UiState
import com.alexis.search.ui.data.local.room.entity.CityEntityBuilder
import com.alexis.search.ui.data.local.room.entity.toDomain
import com.alexis.search.ui.route.Route
import com.google.maps.android.compose.rememberCameraPositionState
import io.mockk.every
import io.mockk.junit4.MockKRule
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf
import org.junit.Rule
import org.junit.Test

private const val SEARCH_TAG = "searchTag"
private const val NAVIGATE_DETAIL_CITY_TAG = "navigateDetailCityTag"

class HomeScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @get:Rule
    val mockkRule = MockKRule(this)

    private val mockNavController = mockk<NavHostController>(relaxed = true)

    private fun setScreenContent(
        query: String = "",
        citiesFake: List<City> = emptyList(),
        navController: NavHostController = mockNavController
    ) {
        val homeViewModelMock = mockk<HomeViewModel>()
        val searchQuery = MutableStateFlow("")

        composeTestRule.setContent {
            every { homeViewModelMock.state } returns MutableStateFlow(UiState.Success(Unit))
            every { homeViewModelMock.searchQuery } returns searchQuery
            every { homeViewModelMock.searchCity(any()) } answers {
                searchQuery.value = query
            }
            every { homeViewModelMock.cities } returns flowOf(PagingData.from(citiesFake))
            HomeScreen(
                modifier = Modifier.fillMaxSize(),
                navController = navController,
                cameraPositionState = rememberCameraPositionState(),
                homeViewModel = homeViewModelMock
            )
        }
    }

    @Test
    fun homeScreen_displaysCities_Success() {
        setScreenContent(
            citiesFake = CityEntityBuilder().toDomain()
        )

        composeTestRule.onNodeWithText("Bogota-CO").assertIsDisplayed()
        composeTestRule.onNodeWithText("Miami Gardens-US").assertIsDisplayed()
        composeTestRule.onNodeWithText("Miami Shores-US").assertIsDisplayed()

    }

    @Test
    fun homeScreen_searchCities_Success() {
        val query = "Medellin"
        setScreenContent(
            query = query,
            citiesFake = CityEntityBuilder().toDomain()
        )

        val textFieldSearch = composeTestRule.onNodeWithTag(SEARCH_TAG)
        textFieldSearch.assertIsDisplayed()
        textFieldSearch.performClick()
        textFieldSearch.performTextInput(query)

        composeTestRule.onNodeWithText(query).assertIsDisplayed()
    }

    @Test
    fun homeScreen_navigateToDetail_Success() {
        val citiesFake = CityEntityBuilder().toDomain()
        val citySelectedId = citiesFake[0].id

        setScreenContent(
            citiesFake = citiesFake
        )

        val buttonCity = composeTestRule.onNodeWithTag("$NAVIGATE_DETAIL_CITY_TAG-$citySelectedId")
        buttonCity.assertIsDisplayed()
        buttonCity.performClick()

        verify { mockNavController.navigate(Route.Detail.createRoute(citySelectedId)) }
    }

}