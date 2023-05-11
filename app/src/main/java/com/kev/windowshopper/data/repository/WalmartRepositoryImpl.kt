package com.kev.windowshopper.data.repository

import com.kev.windowshopper.data.db.WatchListDao
import com.kev.windowshopper.domain.model.Product
import com.kev.windowshopper.domain.repository.WalmartRepository
import com.kev.windowshopper.util.Constants
import com.kev.windowshopper.util.NetworkResult
import java.io.IOException
import java.net.SocketTimeoutException
import org.jsoup.Jsoup
import javax.inject.Inject

class WalmartRepositoryImpl @Inject constructor(
    private val dao: WatchListDao
): WalmartRepository {


    override suspend fun addProductToWatchList(product: Product) = dao.addProductToWatchList(product)

  override  suspend fun searchProducts(query: String): NetworkResult<List<Product>> {
        val productsList = mutableListOf<Product>()

        return try {
            val url = Constants.WALMART_BASE_URL.plus(query)
            val document = Jsoup.connect(url)
                .userAgent("Mozilla")
                .get()

            val productElements = document.select("div.mb0.ph1.pa0-xl.bb.b--near-white.w-25")

            for (i in 0..productElements.size) {
                val productName =
                    productElements.select("a.absolute.w-100.h-100.z-1.hide-sibling-opacity")
                        .eq(i)
                        .text()

                val productPrice = productElements.select("div.b.black.mr1.lh-copy.f5.f4-l").text()
                val ratingElement =
                    productElements.select("div.flex.items-center.mt2").select("span.w_iUH7").text()
                val totalRating = ratingElement.substringBefore("out of").toFloatOrNull() ?: 0f
                val totalReviewsElement =
                    productElements.select("span.sans-serif.gray.f7").eq(i).text()
                val totalReviews = totalReviewsElement.toIntOrNull() ?: 0
                val productLink =
                    productElements.select("a.absolute.w-100.h-100.z-1.hide-sibling-opacity")
                        .attr("href")
                val productImageLink =
                    productElements.select("img.absolute.top-0.left-0").attr("src")

                val product = Product(
                    productName = productName,
                    productPrice = productPrice,
                    productRating = totalRating,
                    totalReviews = totalReviews,
                    productLink = productLink,
                    productImageLink = productImageLink
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
