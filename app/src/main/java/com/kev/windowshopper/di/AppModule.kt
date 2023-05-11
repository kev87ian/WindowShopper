package com.kev.windowshopper.di

import android.content.Context
import androidx.room.Room
import com.kev.windowshopper.domain.db.WatchListDao
import com.kev.windowshopper.domain.db.WatchListDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun providesDatabase(@ApplicationContext context: Context): WatchListDatabase {
        return Room.databaseBuilder(
            context,
            WatchListDatabase::class.java,
            "watchlist_db"
        ).build()
    }

    @Singleton
    @Provides
    fun providesWatchListDao(database: WatchListDatabase): WatchListDao {
        return database.watchListDao()
    }
}