package com.app.reelsapp.data

import com.app.reelsapp.data.local.dto.ProductDto
import com.app.reelsapp.data.local.dto.UserContentDto

interface LocalDataSource {
    suspend fun getProducts(page: Int, size: Int): List<ProductDto>
    suspend fun getUserContent(page: Int, size: Int): List<UserContentDto>
}