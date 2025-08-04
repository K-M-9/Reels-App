package com.app.reelsapp.core.domain.repository

interface UserRepository {
    suspend fun getCurrentUsername(): Result<String?>
    suspend fun toggleFavoriteStatusForUserProduct(
        username: String,
        productID: String,
        isFavorite: Boolean
    ): Result<Unit>

    suspend fun toggleProductOwnerFollowStatusForUser(
        username: String,
        productOwner: String,
        isFollow: Boolean
    ): Result<Unit>
}