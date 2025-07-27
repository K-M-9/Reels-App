package com.app.reelsapp.reels.data

import com.app.reelsapp.reels.data.local.database.dto.ProductDto
import com.app.reelsapp.reels.data.local.database.dto.ProductOwnerDto

interface LocalDataSource {
    suspend fun getUserProductFavorite(username: String): List<String>
    suspend fun getUserProductOwnerFollow(username: String): List<String>
    suspend fun toggleFavoriteStatusForUserProduct(username: String, productID: String, isFavorite: Boolean)
    suspend fun toggleProductOwnerFollowStatusForUser(username: String, productOwner: String, isFollow: Boolean)
    suspend fun getCurrentUsername(): String?
    suspend fun getProducts(page: Int, size: Int): List<ProductDto>
    suspend fun getProductOwner(page: Int, size: Int): List<ProductOwnerDto>
}