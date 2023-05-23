package com.kev.windowshopper.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.kev.windowshopper.domain.model.Product

@Entity(tableName = "watchlist")
data class ProductEntity(
    val productName: String,
    val productImageLink: String,
    @PrimaryKey(autoGenerate = false)
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


