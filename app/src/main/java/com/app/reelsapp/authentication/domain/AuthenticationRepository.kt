package com.app.reelsapp.authentication.domain

interface AuthenticationRepository {
    suspend fun login(username: String): Boolean
    suspend fun register(username: String,name:String): Boolean
}