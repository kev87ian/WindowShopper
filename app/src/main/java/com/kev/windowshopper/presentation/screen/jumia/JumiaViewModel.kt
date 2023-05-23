package com.kev.windowshopper.presentation.screen.jumia

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kev.windowshopper.domain.model.Product
import com.kev.windowshopper.domain.repository.JumiaRepository
import com.kev.windowshopper.presentation.screen.common.ScreenState
import com.kev.windowshopper.util.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class JumiaViewModel @Inject constructor(

    private val repository: JumiaRepository

) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    fun updateSearchQuery(query: String) {
        viewModelScope.launch(Dispatchers.IO) {
            delay(5000L)
            _searchQuery.value = query

            repository.searchProducts(_searchQuery.value).collect { result ->

                _state.value = _state.value.copy(isLoading = true)

                when (result) {
                    is NetworkResult.Loading -> {
                        _state.value = ScreenState(isLoading = true)
                    }

                    is NetworkResult.Error -> {
                        _state.value = ScreenState(errorMessage = result.message, isLoading = false)
                    }

                    is NetworkResult.Success -> {
                        _state.value = ScreenState(
                            products = result.data,
                            isLoading = false
                        )
                    }
                }
            }
        }
    }

    private val _state = mutableStateOf(ScreenState())
    val state = _state

    fun searchProduct(query: String) = viewModelScope.launch(Dispatchers.IO) {
    }
    fun addProductToWatchList(product: Product) = viewModelScope.launch(Dispatchers.IO) {
        repository.addProductToWatchList(product)
    }
}
