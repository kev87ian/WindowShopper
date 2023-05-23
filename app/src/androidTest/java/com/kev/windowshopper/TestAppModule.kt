package com.kev.windowshopper

import android.content.Context
import androidx.room.Room
import com.kev.windowshopper.data.local.WatchListDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object TestAppModule {

    @Provides
    fun provideInMemoryDb(@ApplicationContext context: Context) = Room.inMemoryDatabaseBuilder(
        context,
        WatchListDatabase::class.java
    ).allowMainThreadQueries().build()

}