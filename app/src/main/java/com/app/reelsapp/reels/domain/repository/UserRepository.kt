package com.app.reelsapp.reels.domain.repository

interface UserRepository {
    suspend fun getCurrentUsername(): String?
    suspend fun toggleFavoriteStatusForUserProduct(
        username: String,
        productID: String,
        isFavorite: Boolean
    )

    suspend fun toggleProductOwnerFollowStatusForUser(
        username: String,
        productOwner: String,
        isFollow: Boolean
    )
}