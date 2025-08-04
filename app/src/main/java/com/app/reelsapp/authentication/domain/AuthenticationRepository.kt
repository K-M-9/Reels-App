package com.app.reelsapp.authentication.domain

interface AuthenticationRepository {
    suspend fun login(username: String): Result<Boolean>
    suspend fun register(username: String,name:String): Result<Boolean>
}