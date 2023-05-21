package com.kev.windowshopper.presentation.screen.common

import com.kev.windowshopper.domain.model.Product

data class ScreenState(
    val products: List<Product> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String = ""
)
