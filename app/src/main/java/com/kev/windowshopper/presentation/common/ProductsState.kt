package com.kev.windowshopper.presentation.common

import com.kev.windowshopper.domain.model.Product

data class ProductsState(
    val products: List<Product> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String = "",
    val noResultsFound: Boolean = false
)
