package com.kev.windowshopper.di

import android.content.Context
import androidx.room.Room
import com.kev.windowshopper.data.db.WatchListDao
import com.kev.windowshopper.data.db.WatchListDatabase
import com.kev.windowshopper.data.repository.AmazonRepositoryImpl
import com.kev.windowshopper.data.repository.JumiaRepositoryImpl
import com.kev.windowshopper.data.repository.WalmartRepositoryImpl
import com.kev.windowshopper.domain.repository.AmazonRepository
import com.kev.windowshopper.domain.repository.JumiaRepository
import com.kev.windowshopper.domain.repository.WalmartRepository
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

    @Singleton
    @Provides
    fun providesJumiaRepository(dao: WatchListDao): JumiaRepository {
        return JumiaRepositoryImpl(dao)
    }

    @Singleton
    @Provides
    fun providesAmazonRepository(dao: WatchListDao): AmazonRepository {
        return AmazonRepositoryImpl(dao)
    }

    @Singleton
    @Provides
    fun providesWalmartRepository(dao: WatchListDao): WalmartRepository {
        return WalmartRepositoryImpl(dao)
    }
}
