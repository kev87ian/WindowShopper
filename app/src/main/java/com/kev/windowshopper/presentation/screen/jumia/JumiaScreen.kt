package com.kev.windowshopper.presentation.screen.jumia

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.kev.windowshopper.presentation.common.ErrorState
import com.kev.windowshopper.presentation.common.ProductItemComposable
import com.kev.windowshopper.presentation.screen.common.LoadingState

@Composable
fun JumiaItemsScreen(
    viewModel: JumiaViewModel
) {
    val state = viewModel.state.value

    Column {
        if (state.isLoading) {
            LoadingState()
        }
        if (state.products.isNotEmpty()) {
            LazyColumn(modifier = Modifier.padding(8.dp)) {
               /* items(state.products.size) { i ->
                    val product = state.products[i]
                    ProductItemComposable(product = product)
                    Spacer(modifier = Modifier.height(4.dp))*/

                items(state.products) {
                    ProductItemComposable(product = it)
                }
            }
            Toast.makeText(LocalContext.current, "Succcess", Toast.LENGTH_SHORT).show()
        }
        if (state.errorMessage.isNotEmpty()) {
            ErrorState(errorMessage = state.errorMessage)
        }
    }
}
