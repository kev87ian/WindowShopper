package com.kev.windowshopper.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kev.windowshopper.model.Product
import java.nio.file.WatchEvent

@Database(
    entities = [Product::class],
    version = 1,
    exportSchema = false
)
abstract class WatchListDatabase: RoomDatabase(){
    abstract fun watchListDao() : WatchListDao
}