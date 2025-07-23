package com.app.reelsapp.data.repository

import com.app.reelsapp.data.LocalDataSource
import com.app.reelsapp.domain.repository.UserRepository

class UserRepositoryImpl(
    private val localDataSource: LocalDataSource
) : UserRepository {

    override suspend fun getCurrentUsername(): String? =
        localDataSource.getCurrentUsername()

    override suspend fun toggleFavoriteStatusForUserProduct(
        username: String,
        productID: String,
        isFavorite: Boolean
    ) {
        localDataSource.toggleFavoriteStatusForUserProduct(username, productID, isFavorite)
    }

    override suspend fun toggleProductOwnerFollowStatusForUser(
        username: String,
        productOwner: String,
        isFollow: Boolean
    ) {
        localDataSource.toggleProductOwnerFollowStatusForUser(username, productOwner, isFollow)
    }
}