package com.kev.windowshopper.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "watchlist")
data class Product(
    val productName: String,
    val productImageLink: String,
    @PrimaryKey(autoGenerate = false)
    val productLink: String,
    val productPrice: String,
    val productRating: Float,
    val totalReviews: Int
)
