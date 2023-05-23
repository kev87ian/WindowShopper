package com.kev.windowshopper.presentation.screen.jumia

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kev.windowshopper.domain.model.Product
import com.kev.windowshopper.domain.usecase.SearchJumiaUseCase
import com.kev.windowshopper.util.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class JumiaViewModel @Inject constructor(
    private val useCase: SearchJumiaUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(ProductsState())
    val state = _state

    fun searchProducts(query: String) = viewModelScope.launch(Dispatchers.IO) {
        _state.update {
            it.copy(
                isLoading = true
            )
        }
        useCase.searchJumia(query).collect { result ->

            when (result) {
                is NetworkResult.Loading -> {
                    _state.update {
                        it.copy(
                            isLoading = true
                        )
                    }
                }
                is NetworkResult.Success -> {
                    _state.update {
                        it.copy(
                            products = result.data,
                            isLoading = false
                        )
                    }
                }
                is NetworkResult.Error -> {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = result.message,
                            products = emptyList()
                        )
                    }
                }
            }
        }
    }
    data class ProductsState(
        val products: List<Product> = emptyList(),
        val isLoading: Boolean = false,
        val errorMessage: String = ""
    )
    /*    private val _state = mutableStateOf(ProductsState())
        val state: State<ProductsState> = _state*/
}
