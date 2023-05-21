package com.kev.windowshopper.domain.repository

import com.kev.windowshopper.domain.model.Product
import com.kev.windowshopper.util.NetworkResult
import kotlinx.coroutines.flow.Flow

interface JumiaRepository {
    suspend fun addProductToWatchList(product: Product)
    suspend fun searchProducts(query: String): Flow<NetworkResult<List<Product>>>
}