package com.app.reelsapp.data.local.database.dto

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "product")
data class ProductDto(
    @PrimaryKey val id: Int,
    val name: String,
    val description: String,
    val price: Double,
    val video_url: String
)