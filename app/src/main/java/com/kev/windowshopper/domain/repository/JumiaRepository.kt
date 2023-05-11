package com.kev.windowshopper.domain.repository

import com.kev.windowshopper.domain.model.Product
import com.kev.windowshopper.util.NetworkResult

interface JumiaRepository {
    suspend fun addProductToWatchList(product: Product)
    suspend fun searchProducts(query: String): NetworkResult<List<Product>>
}