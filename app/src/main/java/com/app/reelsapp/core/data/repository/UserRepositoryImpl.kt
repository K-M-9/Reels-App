package com.app.reelsapp.core.data.repository

import com.app.reelsapp.core.domain.repository.UserRepository
import com.app.reelsapp.reels.data.LocalDataSource

class UserRepositoryImpl(
    private val localDataSource: LocalDataSource
) : UserRepository {

    override suspend fun getCurrentUsername(): Result<String?> {
        return runCatching {
            localDataSource.getCurrentUsername()
        }
    }

    override suspend fun toggleFavoriteStatusForUserProduct(
        username: String,
        productID: String,
        isFavorite: Boolean
    ): Result<Unit> {
        return runCatching {
            localDataSource.toggleFavoriteStatusForUserProduct(username, productID, isFavorite)
        }
    }

    override suspend fun toggleProductOwnerFollowStatusForUser(
        username: String,
        productOwner: String,
        isFollow: Boolean
    ): Result<Unit> {
        return runCatching {
            localDataSource.toggleProductOwnerFollowStatusForUser(username, productOwner, isFollow)
        }
    }
}