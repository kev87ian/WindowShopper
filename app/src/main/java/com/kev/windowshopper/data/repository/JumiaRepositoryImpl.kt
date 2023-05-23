package com.kev.windowshopper.data.repository

import com.kev.windowshopper.data.local.WatchListDao
import com.kev.windowshopper.domain.model.Product
import com.kev.windowshopper.domain.repository.JumiaRepository
import com.kev.windowshopper.util.Constants
import com.kev.windowshopper.util.NetworkResult
import java.io.IOException
import javax.inject.Inject
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
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
            delay(5000L)
            try {
                val url = Constants.JUMIA_BASE_URL.plus(query)
                val document = Jsoup.connect(url)
                    .userAgent(
                        "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/40.0.2214.38 Safari/537.36" // ktlint-disable max-line-length
                    )
                    .referrer("https://www.google.com")
                    .get()
                val productElements = document.select("article.prd._fb.col.c-prd")
                for (i in 0 until productElements.size) {
                    val title = productElements.select(".name").eq(i)
                        .text()

                    val price = productElements.select(".prc")
                        .eq(i)
                        .text()
                    val reviewsCountElement = productElements.select("div.rev")
                        .eq(i)
                        .text()

                    val imageUrl = productElements.select("div.img-c")
                        .select("img.img")
                        .eq(i)
                        .attr("data-src")

                    val productLink = productElements.select(".core")
                        .attr("href")
                    // extracts rating value
                    val productRating =
                        reviewsCountElement.substringBefore(" out of").toFloatOrNull() ?: 0f
                    // Extract ratings count
                    val totalReviews =
                        reviewsCountElement.substringAfter("(").substringBefore(")").trim()
                            .toIntOrNull() ?: 0

                    val product = Product(
                        title,
                        price,
                        imageUrl,
                        productLink,
                        productRating,
                        totalReviews
                    )

                    productsList.add(product)
                }
                emit(NetworkResult.Success(productsList))
            } catch (e: IOException) {
                e.printStackTrace()
                emit(NetworkResult.Error("Ensure you have an active internet connection"))
            } catch (e: HttpStatusException) {
                e.printStackTrace()
                emit(NetworkResult.Error(e.localizedMessage ?: "An error occured"))
            } catch (e: Exception) {
                e.printStackTrace()
                emit(NetworkResult.Error(e.localizedMessage ?: "An error occured"))
            }
        }
    }
}
