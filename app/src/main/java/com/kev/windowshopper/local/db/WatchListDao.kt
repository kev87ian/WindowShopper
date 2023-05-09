package com.kev.windowshopper.local.db

import androidx.lifecycle.LiveData
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.kev.windowshopper.model.Product

interface WatchListDao {
    @Query("SELECT * FROM WATCHLIST")
    fun getAllProducts(): List<LiveData<Product>>

    @Insert
    suspend fun addProductToWatchList(product: Product)

    @Delete
    suspend fun deleteProduct(product: Product)
}