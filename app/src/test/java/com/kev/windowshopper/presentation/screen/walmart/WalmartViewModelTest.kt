package com.kev.windowshopper.presentation.screen.walmart

import com.google.common.truth.Truth
import com.kev.windowshopper.domain.model.Product
import com.kev.windowshopper.domain.repository.WalmartRepository
import com.kev.windowshopper.util.NetworkResult
import com.kev.windowshopper.presentation.screen.common.ScreenState
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class WalmartViewModelTest {

    private val repository: WalmartRepository = mockk()
    private var viewModel: WalmartViewModel = mockk()

    @Before
    fun setup(){
        viewModel = WalmartViewModel(repository)
    }



    @Test
    fun searchProduct_error() = runBlocking {
        // Arrange
        val errorMessage = "No Internet Connection."
        val query = "query"
        coEvery { repository.searchProducts(query) } returns NetworkResult.Error(errorMessage)
        //Act
        viewModel.searchProduct(query)
        /*    delay to ensure stateflow has updated its value*/
        delay(300)
        // Assert
        val actualState = viewModel.productsStateFlow.value
        val expectedState = ScreenState.Error(errorMessage)
        Truth.assertThat(expectedState).isEqualTo(actualState)

    }

    @Test
    fun searchProduct_success() = runBlocking {
        // Arrange
        val query = "query"
        val mockProduct = Product("Nokia", "imageUrl", "productLink", "2000", 3.5f, 23)
        val mockProduct2 = Product("Xiaomi", "imageUrl", "productLink", "2000", 4.5f, 20)
        val mockProductsList = mutableListOf<Product>()
        mockProductsList.add(mockProduct)
        mockProductsList.add(mockProduct2)
        coEvery { repository.searchProducts(query) } returns NetworkResult.Success(mockProductsList)
        // Act
        viewModel.searchProduct(query)
        /*         delay to allow the stateflow ot update its value*/
        delay(300)
        // Assert
        val actualState = viewModel.productsStateFlow.value
        val expectedState = ScreenState.Success(data = mockProductsList)

        Truth.assertThat(actualState).isEqualTo(expectedState)
    }
}

