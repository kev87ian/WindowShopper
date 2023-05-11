package com.kev.windowshopper.domain.repository

import com.kev.windowshopper.domain.db.WatchListDao
import com.kev.windowshopper.domain.model.Product
import com.kev.windowshopper.util.Constants
import com.kev.windowshopper.util.NetworkResult
import java.io.IOException
import java.net.SocketTimeoutException
import org.jsoup.Jsoup
import javax.inject.Inject

class JumiaRepository @Inject constructor(
    private val dao: WatchListDao
){

    suspend fun addProductToWatchList(product: Product) = dao.addProductToWatchList(product)




    suspend fun searchProduct(query: String): NetworkResult<List<Product>> {
        val productsList = mutableListOf<Product>()

        return try {
            val url = Constants.JUMIA_BASE_URL.plus(query)
            val document = Jsoup.connect(url)
                .userAgent("Mozilla")
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
                val productRating = reviewsCountElement.substringBefore(" out of").toFloatOrNull() ?: 0f
                // Extract ratings count
                val totalReviews = reviewsCountElement.substringAfter("(").substringBefore(")").trim().toIntOrNull() ?: 0

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
            return NetworkResult.Success(productsList)
        } catch (e: Exception) {
            e.printStackTrace()
            return NetworkResult.Error(e.localizedMessage ?: "An unknown error occured")
        } catch (e: IOException) {
            e.printStackTrace()
            return NetworkResult.Error("Please ensure you have an active internet connection")
        } catch (e: SocketTimeoutException) {
            e.printStackTrace()
            return NetworkResult.Error("The connection timed out. Please retry")
        }
    }
}
