package com.kev.windowshopper.presentation.screen.amazon

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kev.windowshopper.presentation.common.ErrorState
import com.kev.windowshopper.presentation.common.ProductItemComposable
import com.kev.windowshopper.presentation.screen.common.LoadingState

@Composable
fun AmazonItemsScreen(
    viewModel: AmazonViewModel
) {
    val state = viewModel.state.value

    if (state.isLoading) {
        LoadingState()
    }
    if (state.errorMessage.isNotEmpty()) {
        ErrorState(errorMessage = state.errorMessage)
    }

    if (state.products.isNotEmpty()) {
        LazyVerticalGrid(columns = GridCells.Fixed(2), modifier = Modifier.padding(8.dp)) {
            items(state.products) {
                ProductItemComposable(product = it)
            }
        }
    }

  /*  if (state.products.isNotEmpty()) {
        LazyColumn(modifier = Modifier.padding(bottom = 72.dp)) {
            items(state.products) {
                ProductItemComposable(product = it)
            }
        }
    }*/
}
