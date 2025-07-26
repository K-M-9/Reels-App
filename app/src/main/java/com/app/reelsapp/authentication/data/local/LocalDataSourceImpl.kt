package com.app.reelsapp.authentication.data.local

import com.app.reelsapp.authentication.data.LocalDataSource
import com.app.reelsapp.core.data.local.CurrentUserPreferences
import com.app.reelsapp.core.data.local.database.dao.UserDao
import com.app.reelsapp.core.data.local.database.dto.UserDto
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(
    private val userDao: UserDao,
    private val currentUserPreferences: CurrentUserPreferences
) : LocalDataSource {

    override suspend fun login(username: String): Boolean {
        return userDao.getUserByUsername(username)?.let {
            currentUserPreferences.clear()
            currentUserPreferences.saveUsername(username)
            true
        } ?: false
    }

    override suspend fun register(username: String, name: String): Boolean {
        userDao.insert(UserDto(username = username, name = name))
        currentUserPreferences.clear()
        currentUserPreferences.saveUsername(username)
        return true
    }
}
