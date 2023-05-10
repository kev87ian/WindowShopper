package com.kev.windowshopper.local.db

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth
import com.kev.windowshopper.db.WatchListDao
import com.kev.windowshopper.db.WatchListDatabase
import com.kev.windowshopper.getOrAwaitValue
import com.kev.windowshopper.model.Product
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.jupiter.api.Test
import org.junit.runner.RunWith

/*
@ExtendWith(InstantExecutorExtension::class)
class WatchListDatabaseTest {

    @get: Rule
    var instantExecutorRule = InstantTaskExecutorRule()


    private lateinit var database: WatchListDatabase
    private lateinit var dao: WatchListDao

    @BeforeEach
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(context, WatchListDatabase::class.java).build()
        dao = database.watchListDao()
    }

    @AfterEach
    fun teardown() {
        database.close()
    }

    @Test
    fun addProductToWatchlistTest() = runBlocking {
        val product = Product("Xiaomi", "imageUrl", "productLink", 2000, 3.5f, 23.0f)
        dao.addProductToWatchList(product)
        val allProducts = dao.getAllProducts().getOrAwaitValue()
        Truth.assertThat(allProducts).contains(product)
    }
}
*/
@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(AndroidJUnit4::class)
@SmallTest
class WatchListDatabaseTest {
    @get: Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: WatchListDatabase
    private lateinit var dao: WatchListDao

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(context, WatchListDatabase::class.java).build()
        dao = database.watchListDao()
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun addProductToWatchListTest() = runTest {
        val product = Product("Xiaomi", "imageUrl", "productLink", 2000, 3.5f, 23.0f)
        dao.addProductToWatchList(product)
        val allProducts = dao.getAllProducts().getOrAwaitValue()
        Truth.assertThat(allProducts).contains(product)
    }

    @Test
    fun deleteProductFromWatchList() = runTest {
        val product = Product("Xiaomi", "imageUrl", "productLink", 2000, 3.5f, 23.0f)
        dao.addProductToWatchList(product)
        dao.deleteProduct(product)
        val allProducts = dao.getAllProducts().getOrAwaitValue()
        Truth.assertThat(allProducts).doesNotContain(product)
    }
}
