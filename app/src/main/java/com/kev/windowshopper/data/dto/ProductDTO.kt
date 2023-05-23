package com.kev.windowshopper.data.dto

import com.kev.windowshopper.domain.model.Product

data class ProductDTO(
    val productName: String,
    val productImageLink: String,
    val productLink: String,
    val productPrice: String,
    val productRating: Float,
    val totalReviews: Int
){
    fun toProduct(): Product{
        return Product(
            productName = productName,
            productImageLink = productImageLink,
            productLink = productLink,
            productPrice =productPrice,
            productRating = productRating,
            totalReviews = totalReviews
        )
    }
}
