package com.kev.windowshopper.screen.amazon

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kev.windowshopper.repository.AmazonRepository
import com.kev.windowshopper.util.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@HiltViewModel
class AmazonViewModel @Inject constructor(
    val repository: AmazonRepository
) : ViewModel() {

    private val _productsList: MutableState<AmazonScreenState> =
        mutableStateOf(AmazonScreenState.Loading)
    val productList = _productsList

    fun searchProduct(query: String) = viewModelScope.launch(Dispatchers.IO) {
        when (val result = repository.searchProducts(query)) {
            is NetworkResult.Success -> {
                _productsList.value = AmazonScreenState.Success(result.data)
            }

            is NetworkResult.Loading -> {
                _productsList.value = AmazonScreenState.Loading
            }

            is NetworkResult.Error -> {
                _productsList.value = AmazonScreenState.Error(result.message)
            }
        }
    }
}
