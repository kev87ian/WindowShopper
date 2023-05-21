/*
package com.kev.windowshopper.presentation

import androidx.lifecycle.ViewModel
import com.kev.windowshopper.domain.model.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class SearchProductViewModel  : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    private val _searchResults = MutableStateFlow<List<Product>>(emptyList())
    val searchResults: StateFlow<List<Product>> = _searchResults
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _errorMessage = MutableStateFlow("")
    val errorMessage: StateFlow<String> = _errorMessage
    fun setLoadingState(loading: Boolean) {
        _isLoading.value = loading
    }

    fun setErrorMessage(message: String) {
        _errorMessage.value = message
    }

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun updateSearchResults(results: List<Product>) {
        _searchResults.value = results
    }
}
*/
