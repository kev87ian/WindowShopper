package com.kev.windowshopper.domain.repository

import com.kev.windowshopper.domain.model.Product
import com.kev.windowshopper.util.NetworkResult
import kotlinx.coroutines.flow.Flow

interface WalmartRepository {
    suspend fun addProductToWatchList(product: Product)
    suspend fun searchProduct(query: String): Flow<NetworkResult<List<Product>>>
}
