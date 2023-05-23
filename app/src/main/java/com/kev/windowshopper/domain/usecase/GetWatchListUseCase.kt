package com.kev.windowshopper.domain.usecase

import com.kev.windowshopper.data.local.WatchListDao
import javax.inject.Inject

class GetWatchListUseCase @Inject constructor(
    private val dao: WatchListDao
) {
    fun getWatchList() = dao.getAllProducts()
}
