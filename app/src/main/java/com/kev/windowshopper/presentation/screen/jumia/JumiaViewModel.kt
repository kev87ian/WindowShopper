package com.kev.windowshopper.presentation.screen.jumia

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kev.windowshopper.domain.usecase.SearchJumiaUseCase
import com.kev.windowshopper.presentation.common.ProductsState
import com.kev.windowshopper.util.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@HiltViewModel
class JumiaViewModel @Inject constructor(
    private val useCase: SearchJumiaUseCase
) : ViewModel() {

    private val _state = mutableStateOf(ProductsState())
    val state: State<ProductsState> = _state

    /*The job allows us to delay the search if the query changes, and updates the results with results from the new query*/
    private var job: Job? = null

    fun searchProducts(query: String) {
        job?.cancel()
        job = viewModelScope.launch {
            delay(500L)
            useCase.searchJumia(query).onEach { result ->
                when (result) {
                    is NetworkResult.Success -> {
                        if (result.data.isEmpty()) {
                            _state.value = state.value.copy(
                                isLoading = false,
                                errorMessage = "",
                                products = emptyList(),
                                noResultsFound = true
                            )
                        } else {
                            _state.value = state.value.copy(
                                isLoading = false,
                                errorMessage = "",
                                products = result.data,
                                noResultsFound = false
                            )
                        }
                    }

                    is NetworkResult.Error -> {
                        _state.value = state.value.copy(
                            isLoading = false,
                            errorMessage = result.message,
                            products = emptyList(),
                            noResultsFound = false
                        )
                    }

                    is NetworkResult.Loading -> {
                        _state.value = state.value.copy(
                            isLoading = true,
                            errorMessage = "",
                            products = emptyList(),
                            noResultsFound = false
                        )
                        println(result)
                    }
                }
            }.launchIn(viewModelScope)
        }
    }
}
