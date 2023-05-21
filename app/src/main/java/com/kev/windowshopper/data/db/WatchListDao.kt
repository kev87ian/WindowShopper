package com.kev.windowshopper.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kev.windowshopper.domain.model.Product
@Dao
interface WatchListDao {
    @Query("SELECT * FROM WATCHLIST")
    fun getAllProducts(): LiveData<List<Product>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addProductToWatchList(product: Product)

    @Delete
    suspend fun deleteProduct(product: Product)
}