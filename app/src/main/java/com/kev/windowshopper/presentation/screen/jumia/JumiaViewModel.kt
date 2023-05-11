package com.kev.windowshopper.presentation.screen.jumia

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kev.windowshopper.domain.model.Product
import com.kev.windowshopper.data.repository.JumiaRepositoryImpl
import com.kev.windowshopper.domain.repository.JumiaRepository
import com.kev.windowshopper.util.NetworkResult
import com.kev.windowshopper.util.ScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class JumiaViewModel @Inject constructor(
    private val repository: JumiaRepository
) : ViewModel() {

    private val _productsStateFlow = MutableStateFlow<ScreenState>(ScreenState.Loading)
    val productsStateFlow = _productsStateFlow

    fun searchProduct(query: String) = viewModelScope.launch(Dispatchers.IO) {
        when (val result = repository.searchProducts(query)) {
            is NetworkResult.Loading -> {
                _productsStateFlow.value = ScreenState.Loading
            }

            is NetworkResult.Error -> {
                _productsStateFlow.value = ScreenState.Error(errorMessage = result.message)
            }

            is NetworkResult.Success -> {
                _productsStateFlow.value = ScreenState.Success(data = result.data)
            }
        }
    }

    fun addProductToWatchList(product: Product) = viewModelScope.launch(Dispatchers.IO) {
        repository.addProductToWatchList(product)
    }
}
