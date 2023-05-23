package com.kev.windowshopper.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.kev.windowshopper.presentation.screen.amazon.AmazonItemsScreen
import com.kev.windowshopper.presentation.screen.jumia.JumiaItemsScreen
import com.kev.windowshopper.presentation.screen.jumia.JumiaViewModel

@Composable
fun BottomNavGraph(
    navHostController: NavHostController
) {
    val jumiaViewModel = hiltViewModel<JumiaViewModel>()
    val state by jumiaViewModel.state.collectAsState()

    NavHost(navController = navHostController, startDestination = BottomBarScreens.Jumia.route) {
        composable(route = BottomBarScreens.Jumia.route) {
            JumiaItemsScreen(state)
        }

        composable(route = BottomBarScreens.Amazon.route) {
            AmazonItemsScreen()
        }

        composable(route = BottomBarScreens.Walmart.route) {
            Screen(text = "Walmart")
        }
    }
}
sealed class BottomBarScreens(
    val title: String,
    val route: String,
    val icon: ImageVector
) {
    object Jumia : BottomBarScreens(
        title = "Jumia",
        route = "jumia",
        icon = Icons.Default.List
    )

    object Amazon : BottomBarScreens(
        title = "Amazon",
        route = "amazon",
        icon = Icons.Default.List
    )

    object Walmart : BottomBarScreens(
        title = "Walmart",
        route = "walmart",
        icon = Icons.Default.List
    )
}

@Composable
fun Screen(text: String) {
    Text(text = text)
}
