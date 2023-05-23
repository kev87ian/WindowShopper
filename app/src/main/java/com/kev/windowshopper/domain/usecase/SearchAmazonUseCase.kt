package com.kev.windowshopper.domain.usecase

import com.kev.windowshopper.domain.repository.AmazonRepository
import javax.inject.Inject

class SearchAmazonUseCase @Inject constructor(
    private val repository: AmazonRepository
) {
}