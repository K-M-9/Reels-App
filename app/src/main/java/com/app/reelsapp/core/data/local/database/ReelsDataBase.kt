package com.app.reelsapp.core.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.app.reelsapp.reels.data.local.database.dao.ProductDao
import com.app.reelsapp.core.data.local.database.dao.UserDao
import com.app.reelsapp.reels.data.local.database.dto.FavoriteDto
import com.app.reelsapp.reels.data.local.database.dto.FollowDto
import com.app.reelsapp.reels.data.local.database.dto.ProductDto
import com.app.reelsapp.reels.data.local.database.dto.ProductOwnerDto
import com.app.reelsapp.core.data.local.database.dto.UserDto


@Database(
    entities = [UserDto::class, ProductOwnerDto::class, ProductDto::class, FavoriteDto::class, FollowDto::class],
    version = 1,
    exportSchema = false
)

abstract class ReelsDataBase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun productDao(): ProductDao
}
