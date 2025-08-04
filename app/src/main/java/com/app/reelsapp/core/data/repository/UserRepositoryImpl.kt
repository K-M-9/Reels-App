package com.app.reelsapp.core.data.repository

import com.app.reelsapp.core.domain.repository.UserRepository
import com.app.reelsapp.reels.data.ReelsLocalDataSource

class UserRepositoryImpl(
    private val reelsLocalDataSource: ReelsLocalDataSource
) : UserRepository {

    override suspend fun getCurrentUsername(): Result<String?> {
        return runCatching {
            reelsLocalDataSource.getCurrentUsername()
        }
    }

    override suspend fun toggleFavoriteStatusForUserProduct(
        username: String,
        productID: String,
        isFavorite: Boolean
    ): Result<Unit> {
        return runCatching {
            reelsLocalDataSource.toggleFavoriteStatusForUserProduct(username, productID, isFavorite)
        }
    }

    override suspend fun toggleProductOwnerFollowStatusForUser(
        username: String,
        productOwner: String,
        isFollow: Boolean
    ): Result<Unit> {
        return runCatching {
            reelsLocalDataSource.toggleProductOwnerFollowStatusForUser(username, productOwner, isFollow)
        }
    }
}