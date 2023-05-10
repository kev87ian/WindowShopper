package com.kev.windowshopper.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomBarScreens(
    val title:String,
    val route:String,
    val icon: ImageVector
){
    object Jumia: BottomBarScreens(
        title = "Jumia",
        route = "jumia",
        icon = Icons.Default.List
    )

    object Amazon: BottomBarScreens(
        title = "Amazon",
        route = "amazon",
        icon = Icons.Default.List
    )

    object Walmart: BottomBarScreens(
        title = "Walmart",
        route = "walmart",
        icon = Icons.Default.List
    )
}
