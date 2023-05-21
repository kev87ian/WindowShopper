package com.kev.windowshopper.presentation.screen.amazon

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kev.windowshopper.domain.model.Product
import com.kev.windowshopper.domain.repository.AmazonRepository
import com.kev.windowshopper.presentation.screen.common.ScreenState
import com.kev.windowshopper.util.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@HiltViewModel
class AmazonViewModel @Inject constructor(
    private val repository: AmazonRepository
) : ViewModel() {

    private val _state = mutableStateOf(ScreenState())
    val state = _state
    fun addProductToWatchList(product: Product) = viewModelScope.launch(Dispatchers.IO) {
        repository.addProductToWatchList(product)
    }

    private var searchJob: Job? = null
    fun searchProduct(query: String) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch(Dispatchers.IO) {
            delay(5000L)

            when (val result = repository.searchProducts(query)) {
                is NetworkResult.Loading -> {
                    _state.value = ScreenState(isLoading = true)
                }

                is NetworkResult.Error -> {
                    _state.value = ScreenState(errorMessage = result.message)
                }

                is NetworkResult.Success -> {
                    _state.value = ScreenState(products = result.data)
                }
            }
        }
    }
}
