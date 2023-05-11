package com.kev.windowshopper.util

import com.kev.windowshopper.model.Product

sealed class ScreenState {
    object Loading : ScreenState()
    data class Success(val data: List<Product>): ScreenState()
    data class Error(val errorMessage: String): ScreenState()
}