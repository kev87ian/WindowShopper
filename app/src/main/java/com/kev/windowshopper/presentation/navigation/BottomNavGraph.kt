package com.kev.windowshopper.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.kev.windowshopper.presentation.screen.amazon.AmazonItemsScreen
import com.kev.windowshopper.presentation.screen.amazon.AmazonViewModel
import com.kev.windowshopper.presentation.screen.jumia.JumiaItemsScreen
import com.kev.windowshopper.presentation.screen.jumia.JumiaViewModel
import com.kev.windowshopper.presentation.screen.walmart.WalmartScreen
import com.kev.windowshopper.presentation.screen.walmart.WalmartViewModel

@Composable
fun BottomNavGraph(
    navHostController: NavHostController
) {
    val jumiaViewModel = hiltViewModel<JumiaViewModel>()
    val amazonViewModel = hiltViewModel<AmazonViewModel>()
    val walmartViewModel = hiltViewModel<WalmartViewModel>()

    NavHost(navController = navHostController, startDestination = BottomBarScreens.Amazon.route) {
        composable(route = BottomBarScreens.Amazon.route) {
            AmazonItemsScreen(viewModel = amazonViewModel)
        }
        composable(route = BottomBarScreens.Jumia.route) {
            JumiaItemsScreen(viewModel = jumiaViewModel)
            navHostController.enableOnBackPressed(false)
        }

        composable(route = BottomBarScreens.Walmart.route) {
            // navHostController.popBackStack()
            WalmartScreen(viewModel = walmartViewModel)
            navHostController.enableOnBackPressed(false)
        }
    }
}

sealed class BottomBarScreens(
    val title: String,
    val route: String,
    val icon: ImageVector
) {
    object Amazon : BottomBarScreens(
        title = "Amazon",
        route = "amazon",
        icon = Icons.Default.List
    )
    object Jumia : BottomBarScreens(
        title = "Jumia",
        route = "jumia",
        icon = Icons.Default.List
    )

    object Walmart : BottomBarScreens(
        title = "Walmart",
        route = "walmart",
        icon = Icons.Default.List
    )
}
