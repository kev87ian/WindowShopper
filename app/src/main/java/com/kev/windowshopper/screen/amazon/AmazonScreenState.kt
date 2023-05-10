package com.kev.windowshopper.screen.amazon

import com.kev.windowshopper.model.Product

sealed class AmazonScreenState {
    object Loading : AmazonScreenState()
    data class Success(val data: List<Product>): AmazonScreenState()
    data class Error(val errorMessage: String): AmazonScreenState()
}