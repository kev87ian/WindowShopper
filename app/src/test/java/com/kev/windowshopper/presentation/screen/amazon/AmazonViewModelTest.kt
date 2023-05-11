package com.kev.windowshopper.presentation.screen.amazon

import com.google.common.truth.Truth
import com.kev.windowshopper.domain.model.Product
import com.kev.windowshopper.domain.repository.AmazonRepository
import com.kev.windowshopper.util.NetworkResult
import com.kev.windowshopper.util.ScreenState
import io.mockk.coEvery
import io.mockk.junit4.MockKRule
import io.mockk.mockk
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.internal.runners.JUnit4ClassRunner
import org.junit.runner.RunWith

@RunWith(MockKRule)
class AmazonViewModelTest {
    private val repository: AmazonRepository = mockk()
    private var amazonViewModel: AmazonViewModel = mockk()

    @Before
    fun setup() {
        amazonViewModel = AmazonViewModel(repository)
    }

    @Test
    fun searchProduct_error() = runBlocking {
        // Arrange
        val errorMessage = "No Internet Connection."
        val query = "query"
        coEvery { repository.searchProducts(query) } returns NetworkResult.Error(errorMessage)
        // Act
        amazonViewModel.searchProduct(query)
        /*        the delay allows the stateflow to update before we check it's value*/
        delay(300)
        // Assert
        val actualState = amazonViewModel.productsStateFlow.value
        val expectedState = ScreenState.Error("No Internet Connection.")
        Truth.assertThat(actualState).isEqualTo(expectedState)
    }

    @Test
    fun searchProduct_success() = runBlocking {
        // Arrange
        val mockProduct = Product("Xiaomi", "imageUrl", "productLink", "2000", 3.5f, 23)
        val mockProductsList = listOf(mockProduct)
        coEvery { repository.searchProducts("query") } returns NetworkResult.Success(
            mockProductsList
        )
        // Act
        amazonViewModel.searchProduct("query")

        // the delay allows the stateflow to update before we check it's value
        delay(300)

        // Assert
        val actualState = amazonViewModel.productsStateFlow.value
        val expectedState = ScreenState.Success(mockProductsList)
        Truth.assertThat(actualState).isEqualTo(expectedState)
    }

}
