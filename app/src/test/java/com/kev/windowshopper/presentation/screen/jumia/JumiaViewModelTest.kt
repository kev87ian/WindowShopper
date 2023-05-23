package com.kev.windowshopper.presentation.screen.jumia

import com.google.common.truth.Truth
import com.kev.windowshopper.domain.model.Product
import com.kev.windowshopper.domain.repository.JumiaRepository
import com.kev.windowshopper.presentation.common.ProductsState
import com.kev.windowshopper.util.NetworkResult
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class JumiaViewModelTest {
    private val repository: JumiaRepository = mockk()
    private var jumiaViewModel: JumiaViewModel = mockk()

    @Before
    fun setup() {
        jumiaViewModel = JumiaViewModel(repository)
    }

    @Test
    fun searchProduct_error() = runBlocking {
        // Arrange
        val errorMessage = "No Internet Connection."
        val query = "query"
        coEvery { repository.searchProducts(query) } returns NetworkResult.Error(errorMessage)
        // Act
        jumiaViewModel.searchProducts(query)
        /*    delay to ensure stateflow has updated its value*/
        delay(300)
        // Assert
        val actualState = jumiaViewModel.productsStateFlow.value
        val expectedState = ProductsState.Error(errorMessage)
        Truth.assertThat(actualState).isEqualTo(expectedState)
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
        jumiaViewModel.searchProducts(query)

/*         delay to allow the stateflow ot update its value*/

        delay(300)

        // Assert
        val actualState = jumiaViewModel.productsStateFlow.value
        val expectedState = ProductsState.Success(data = mockProductsList)

        Truth.assertThat(actualState).isEqualTo(expectedState)
    }
}
