package com.app.reelsapp.reels.data.local.database.dto

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "user")
data class UserDto(
    @PrimaryKey(autoGenerate = false)
    val username: String,
    val name: String,
)
