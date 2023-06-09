package com.kev.windowshopper.data.repository

import com.kev.windowshopper.data.local.WatchListDao
import com.kev.windowshopper.domain.model.Product
import com.kev.windowshopper.domain.repository.JumiaRepository
import com.kev.windowshopper.util.Constants
import com.kev.windowshopper.util.NetworkResult
import java.io.IOException
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import org.jsoup.HttpStatusException
import org.jsoup.Jsoup

class JumiaRepositoryImpl @Inject constructor(
    private val dao: WatchListDao
) : JumiaRepository {

    override suspend fun addProductToWatchList(product: Product) {
        dao.addProductToWatchList(product)
    }

    override suspend fun searchProduct(query: String): Flow<NetworkResult<List<Product>>> {
        // create a mutable list that we'll populate with data
        val productsList = mutableListOf<Product>()

        return flow {
            emit(NetworkResult.Loading())
            try {
                val url = Constants.JUMIA_BASE_URL.plus(query)
                val document = withContext(Dispatchers.IO) {
                    Jsoup.connect(url)
                        .userAgent("Mozilla")
                        .timeout(10000)
                        .referrer("https://www.google.com")
                        .get()
                }

                val productElements = document.select("article.prd._fb.col.c-prd")
                for (i in 0 until productElements.size) {
                    val productName = productElements.select(".name").eq(i)
                        .text()

                    val productLink = productElements.select(".prc")
                        .eq(i)
                        .text()
                    val reviewsCountElement = productElements.select("div.rev")
                        .eq(i)
                        .text()

                    val productPrice = productElements.select("div.img-c")
                        .select("img.img")
                        .eq(i)
                        .attr("data-src")

                    val productImageLink = productElements.select(".core")
                        .attr("href")
                    // extracts rating value
                    val productRating = reviewsCountElement.substringBefore(" out of").toFloatOrNull() ?: 0f
                    // Extract ratings count
                    val totalReviews = reviewsCountElement.substringAfter("(").substringBefore(")").trim().toIntOrNull() ?: 0

                    val product = Product(
                        productName,
                        productPrice,
                        productImageLink,
                        productLink,
                        productRating,
                        totalReviews
                    )

                    if (productImageLink.isNotEmpty()) {
                        productsList.add(product)
                    } else {
                        Unit
                    }

                    for (parsedProduct in productsList) {
                        println("Title: ${parsedProduct.productName}")
                        println("Product Link: ${parsedProduct.productPrice}")
                        println("Image Url: ${parsedProduct.productLink}")
                        println("Price: ${parsedProduct.productImageLink}")
                        println("Rating: ${parsedProduct.productRating}")
                        println("Reviews Count: ${parsedProduct.totalReviews}")
                        println("\n")
                    }
                }
                emit(NetworkResult.Success(productsList))
            } catch (e: IOException) {
                e.printStackTrace()
                emit(NetworkResult.Error("Ensure you have an active internet connection"))
            } catch (e: HttpStatusException) {
                e.printStackTrace()
                emit(NetworkResult.Error(e.localizedMessage ?: "An error occured(Jsoup) "))
            } catch (e: Exception) {
                e.printStackTrace()
                emit(NetworkResult.Error(e.localizedMessage ?: "An error occured"))
            }
        }
    }
}
