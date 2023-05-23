package com.kev.windowshopper.di

import android.content.Context
import androidx.room.Room
import com.kev.windowshopper.data.local.WatchListDao
import com.kev.windowshopper.data.local.WatchListDatabase
import com.kev.windowshopper.data.repository.AmazonRepositoryImpl
import com.kev.windowshopper.data.repository.JumiaRepositoryImpl
import com.kev.windowshopper.data.repository.WalmartRepositoryImpl
import com.kev.windowshopper.domain.repository.AmazonRepository
import com.kev.windowshopper.domain.repository.JumiaRepository
import com.kev.windowshopper.domain.repository.WalmartRepository
import com.kev.windowshopper.domain.usecase.SearchAmazonUseCase
import com.kev.windowshopper.domain.usecase.SearchJumiaUseCase
import com.kev.windowshopper.domain.usecase.SearchWalmartUseCase
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
        ).fallbackToDestructiveMigration().build()
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

    @Singleton
    @Provides
    fun providesJumiaUseCase(repository: JumiaRepository): SearchJumiaUseCase{
        return SearchJumiaUseCase(repository)
    }

    @Singleton
    @Provides
    fun providesAmazonUseCase(repository: AmazonRepository): SearchAmazonUseCase{
        return SearchAmazonUseCase(repository)
    }

    @Singleton
    @Provides
    fun providesWalmartUseCase(repository: WalmartRepository): SearchWalmartUseCase{
        return SearchWalmartUseCase(repository)
    }
}
