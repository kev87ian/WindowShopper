package com.kev.windowshopper.data.repository

import com.kev.windowshopper.data.local.WatchListDao
import com.kev.windowshopper.domain.model.Product
import com.kev.windowshopper.domain.repository.AmazonRepository
import com.kev.windowshopper.util.Constants
import com.kev.windowshopper.util.NetworkResult
import java.io.IOException
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.jsoup.HttpStatusException
import org.jsoup.Jsoup

class AmazonRepositoryImpl @Inject constructor(
    private val dao: WatchListDao
) : AmazonRepository {
    override suspend fun addProductToWatchList(product: Product) {
        dao.addProductToWatchList(product)
    }

    override suspend fun searchProduct(query: String): Flow<NetworkResult<List<Product>>> {
        /* Create a mutable list that we'll populate and submit*/

        val productsList = mutableListOf<Product>()
        return flow {
            emit(NetworkResult.Loading())

            try {
                val url = Constants.AMAZON_BASE_URL.plus(query)
                val doc = Jsoup.connect(url)
                    .userAgent(
                        "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/40.0.2214.38 Safari/537.36"
                    )
                    .referrer("https://www.google.com")
                    .get()

/*                 Select elements/classes that contain results found*/
                val productElements =
                    doc.select(
                        "div.sg-col-12-of-16.s-widget-spacing-small.sg-col.sg-col-16-of-20.sg-col-0-of-12.s-asin.s-result-item.sg-col-20-of-24 > .sg-col-inner"
                    )
                // loop through the results, extracting the data we need
                for (i in 1..productElements.size) {
                    val title =
                        productElements.select("span.a-size-medium.a-color-base.a-text-normal").eq(
                            i
                        )
                            .text()
                    val productLink =
                        productElements.select(
                            "a.a-link-normal.s-underline-text.s-underline-link-text.s-link-style.a-text-normal"
                        )
                            .attr("href")
                    val price = productElements.select("span.a-price-whole").eq(i).text()

                    val productImageLink = productElements.select("img.s-image").eq(i).attr("src")
                    val ratingsElement = productElements.select("span.a-icon-alt").eq(i)
                        .text()
                    val productRating =
                        ratingsElement.substringBefore(" out of").trim().toFloatOrNull() ?: 0f

                    val totalReviews =
                        productElements.select("span.a-size-base.s-underline-text").eq(i)
                            .text().toIntOrNull() ?: 0

                    val product = Product(
                        productName = title,
                        productLink = productLink,
                        productPrice = price,
                        productImageLink = productImageLink,
                        productRating = productRating,
                        totalReviews = totalReviews
                    )

                    productsList.add(product)
                }
                emit(NetworkResult.Success(productsList))
            } catch (e: Exception) {
                e.printStackTrace()
                emit(NetworkResult.Error(e.localizedMessage ?: "Oops, an error occurred"))
            } catch (e: IOException) {
                e.printStackTrace()
                emit(NetworkResult.Error("Please ensure you have an active internet connection."))
            } catch (e: HttpStatusException) {
                e.printStackTrace()
                emit(NetworkResult.Error(e.localizedMessage ?: "Oops, an uncaught error occured"))
            }
        }
    }
}
