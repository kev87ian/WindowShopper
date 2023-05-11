package com.kev.windowshopper.screen.jumia

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kev.windowshopper.db.WatchListDao
import com.kev.windowshopper.repository.JumiaRepository
import com.kev.windowshopper.util.NetworkResult
import com.kev.windowshopper.util.ScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class JumiaViewModel @Inject constructor(
    private val repository: JumiaRepository
) : ViewModel() {


    private val _productsList = MutableStateFlow<ScreenState>(ScreenState.Loading)
    val productsList = _productsList


    fun searchProduct(query: String) = viewModelScope.launch(Dispatchers.IO) {
        when (val result = repository.searchProduct(query)) {
            is NetworkResult.Loading -> {
                _productsList.value = ScreenState.Loading
            }

            is NetworkResult.Error -> {
                _productsList.value = ScreenState.Error(result.message)
            }

            is NetworkResult.Success -> {
                _productsList.value = ScreenState.Success(result.data)
            }
        }

    }
}