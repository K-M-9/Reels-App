package com.app.reelsapp.authentication.data.repository

import com.app.reelsapp.authentication.data.LocalDataSource
import com.app.reelsapp.authentication.domain.AuthenticationRepository

class AuthenticationRepositoryImpl(
    private val localDataSource: LocalDataSource
) : AuthenticationRepository {
    override suspend fun login(username: String): Boolean =
        localDataSource.login(username)

    override suspend fun register(username: String, name: String): Boolean =
        localDataSource.register(username, name)
}