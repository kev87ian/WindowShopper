package com.kev.windowshopper.domain.repository

import android.app.DownloadManager.Query
import com.kev.windowshopper.data.local.entity.ProductEntity
import com.kev.windowshopper.domain.model.Product
import com.kev.windowshopper.util.NetworkResult
import kotlinx.coroutines.flow.Flow

interface JumiaRepository {
/*    suspend fun addProductToWatchList(product: Product)
    suspend fun searchProducts(query: String): Flow<NetworkResult<List<Product>>>*/

    suspend fun addProductToWatchList(productEntity: ProductEntity)
    suspend fun searchProduct(query: String): Flow<NetworkResult<List<Product>>>
}