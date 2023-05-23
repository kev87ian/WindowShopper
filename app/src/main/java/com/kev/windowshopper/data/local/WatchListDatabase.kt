package com.kev.windowshopper.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kev.windowshopper.domain.model.Product

@Database(
    entities = [Product::class],
    version = 1,
    exportSchema = false
)
abstract class WatchListDatabase : RoomDatabase() {
    abstract fun watchListDao(): WatchListDao
}
