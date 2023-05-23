package com.kev.windowshopper.domain.usecase

import com.kev.windowshopper.domain.repository.WalmartRepository
import javax.inject.Inject

class SearchWalmartUseCase @Inject constructor(
    private val repository: WalmartRepository
) {
}