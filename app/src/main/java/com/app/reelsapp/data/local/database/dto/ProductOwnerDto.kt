package com.app.reelsapp.data.local.database.dto

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "product_owner")
data class ProductOwnerDto(
    @PrimaryKey val id: Int,
    val user_id: Int,
    val name: String,
    val title: String,
    val content: String,
    val rating: Int,
    val created_at: String,
    val product_id: Int,
    val image_url: String
)