package com.app.reelsapp.data.local.database.dto

import androidx.room.Entity
import androidx.room.Index

@Entity(
    tableName = "favorite",
    primaryKeys = ["username", "productId"],
    indices = [Index(value = ["username", "productId"], unique = true)]
)
data class FavoriteDto(
    val username: String,
    val productId: String
)

