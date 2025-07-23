package com.app.reelsapp.data.local.database.dto

import androidx.room.Entity
import androidx.room.Index


@Entity(
    tableName = "follow",
    primaryKeys = ["username", "ownerId"],
    indices = [Index(value = ["username", "ownerId"], unique = true)]
)
data class FollowDto(
    val username: String,
    val ownerId: String
)