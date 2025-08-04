package com.app.reelsapp.authentication.data

interface AuthenticationLocalDataSource {
    suspend fun login(username: String): Boolean
    suspend fun register(username: String,name:String): Boolean
}