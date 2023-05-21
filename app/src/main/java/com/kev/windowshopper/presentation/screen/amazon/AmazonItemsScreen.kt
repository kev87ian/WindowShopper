package com.kev.windowshopper.presentation.screen.amazon

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.kev.windowshopper.presentation.screen.common.ErrorState
import com.kev.windowshopper.presentation.screen.common.LoadingState
import com.kev.windowshopper.presentation.screen.common.ProductItemComposable
import com.kev.windowshopper.presentation.screen.common.ScreenState

@Composable
fun AmazonItemsScreen() {
    val viewModel: AmazonViewModel = hiltViewModel()

    val state = viewModel.state

    if (state.value.isLoading) {
        LoadingState()
    }
    if (state.value.errorMessage.isNotEmpty()) {
        ErrorState(errorMessage = state.value.errorMessage)
    }
    if (state.value.products.isNotEmpty()){
        LazyColumn {
            items(state.value.products) {
                ProductItemComposable(product = it)
            }
        }
    }
}
