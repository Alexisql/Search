package com.alexis.search.ui.home

import android.os.Build
import androidx.paging.testing.asSnapshot
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.alexis.search.MainDispatcherRule
import com.alexis.search.data.local.datasource.CityDataSource
import com.alexis.search.data.local.room.CityDataBase
import com.alexis.search.data.local.room.dao.CityDao
import com.alexis.search.data.local.room.entity.CityEntityBuilder
import com.alexis.search.data.local.room.repository.CityRepositoryImpl
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.S])
class HomeViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var cityDao: CityDao
    private lateinit var dataBase: CityDataBase
    private lateinit var viewModel: HomeViewModel

    @Before
    fun initBefore() = runTest {
        initDataBase()
        val cityRepositoryMock =
            CityRepositoryImpl(cityDao, mockk<CityDataSource>(), mainDispatcherRule.testDispatcher)
        viewModel = HomeViewModel(cityRepositoryMock, mainDispatcherRule.testDispatcher)
    }

    @After
    fun closeDatabase() {
        dataBase.close()
    }

    private suspend fun initDataBase() {
        dataBase = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            CityDataBase::class.java
        )
            .allowMainThreadQueries()
            .build()
        cityDao = dataBase.getCityDao()
        cityDao.insertAll(CityEntityBuilder().build())
    }

    @Test
    fun searchCity_validateReturnFlowPagingData_Success() =
        runTest {
            val query = "miami"

            viewModel.searchCity(query)
            val cities = viewModel.cities.asSnapshot()

            assertEquals(2, cities.size)
            assertTrue(cities[0].id == 4164166 && cities[0].name == "Miami Gardens")
            assertTrue(cities[1].id == 4164211 && cities[1].name == "Miami Shores")
        }

    @Test
    fun searchCity_validateReturnFlowPagingData_Favorites() =
        runTest {
            val query = ""

            viewModel.searchCity(query)
            val cities = viewModel.cities.asSnapshot()

            assertEquals(1, cities.size)
            assertTrue(cities[0].id == 3688689 && cities[0].name == "Bogota")
        }

    @Test
    fun searchCity_validateReturnFlowPagingData_Empty() =
        runTest {
            val query = "mexico"

            viewModel.searchCity(query)
            val cities = viewModel.cities.asSnapshot()

            assertEquals(0, cities.size)
        }
}