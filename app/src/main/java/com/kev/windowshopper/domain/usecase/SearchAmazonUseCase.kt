package com.kev.windowshopper.domain.usecase

import com.kev.windowshopper.domain.model.Product
import com.kev.windowshopper.domain.repository.AmazonRepository
import com.kev.windowshopper.util.NetworkResult
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SearchAmazonUseCase @Inject constructor(
    private val repository: AmazonRepository
) {
    suspend fun searchProduct(query: String): Flow<NetworkResult<List<Product>>> {
        if (query.isEmpty()) {
            return flow { }
        }
        return repository.searchProduct(query)
    }
}
