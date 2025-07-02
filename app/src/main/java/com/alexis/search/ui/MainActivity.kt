package com.alexis.search.ui

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.alexis.search.ui.home.HomeScreen
import com.alexis.search.ui.maps.ShowMapScreen
import com.alexis.search.ui.navigation.Navigation
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navHostController = rememberNavController()
            Scaffold { innerPadding ->
                ScreenOrientation(
                    navController = navHostController,
                    innerPadding = innerPadding
                )
            }
        }
    }
}

@Composable
fun ScreenOrientation(navController: NavHostController, innerPadding: PaddingValues) {
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    if (isLandscape) {
        Row(
            modifier = Modifier.fillMaxSize()
        ) {
            Column(modifier = Modifier.weight(0.5f)) {
                HomeScreen(
                    modifier = Modifier,
                    navController = navController
                )
            }
            Box(modifier = Modifier.weight(0.5f)) {
                ShowMapScreen(
                    modifier = Modifier,
                    idCountry = 0
                )
            }
        }
    } else {
        Navigation(
            navController = navController,
            innerPadding = innerPadding
        )
    }
}