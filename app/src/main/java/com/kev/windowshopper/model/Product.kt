package com.kev.windowshopper.model

data class Product(
    val productName:String,
    val productImageLink: String,
    val productLink:String,
    val productPrice: Int,
    val productRating: Float,
    val totalReviews: Float
)