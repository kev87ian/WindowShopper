package com.kev.windowshopper.presentation.screen.amazon

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun AmazonItemsScreen() {
    val viewModel: AmazonViewModel = hiltViewModel()

    // val state = viewModel.state

//    if (state.value.isLoading) {
//        LoadingState()
//    }
//    if (state.value.errorMessage.isNotEmpty()) {
//        ErrorState(errorMessage = state.value.errorMessage)
//    }
}
