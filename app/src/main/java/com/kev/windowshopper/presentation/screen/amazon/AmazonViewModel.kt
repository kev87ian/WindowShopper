package com.kev.windowshopper.presentation.screen.amazon

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kev.windowshopper.domain.model.Product
import com.kev.windowshopper.data.repository.AmazonRepositoryImpl
import com.kev.windowshopper.domain.repository.AmazonRepository
import com.kev.windowshopper.util.ScreenState
import com.kev.windowshopper.util.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class AmazonViewModel @Inject constructor(
    private val repository: AmazonRepository
) : ViewModel() {

    private val _productsStateFlow = MutableStateFlow<ScreenState>(ScreenState.Loading)
    val productsStateFlow = _productsStateFlow

    fun addProductToWatchList(product: Product) = viewModelScope.launch(Dispatchers.IO) {
        repository.addProductToWatchList(product)
    }

    fun searchProduct(query: String) = viewModelScope.launch(Dispatchers.IO) {
        when (val result = repository.searchProducts(query)) {
            is NetworkResult.Success -> {
                _productsStateFlow.value = ScreenState.Success(data = result.data)
            }

            is NetworkResult.Loading -> {
                _productsStateFlow.value = ScreenState.Loading
            }

            is NetworkResult.Error -> {
                _productsStateFlow.value = ScreenState.Error(errorMessage = result.message)
            }
        }
    }
}
