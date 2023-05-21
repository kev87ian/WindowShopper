package com.kev.windowshopper.presentation.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.kev.windowshopper.presentation.screen.amazon.AmazonItemsScreen
import com.kev.windowshopper.presentation.screen.jumia.JumiaItemsScreen

@Composable
fun BottomNavGraph(
    navHostController: NavHostController
) {
    NavHost(navController = navHostController, startDestination = BottomBarScreens.Jumia.route) {
        composable(route = BottomBarScreens.Jumia.route) {
            JumiaItemsScreen()
        }

        composable(route = BottomBarScreens.Amazon.route) {
            AmazonItemsScreen()
        }

        composable(route = BottomBarScreens.Walmart.route) {
            Screen(text = "Walmart")
        }
    }
}

@Composable
fun Screen(text: String) {
    Text(text = text)
}
