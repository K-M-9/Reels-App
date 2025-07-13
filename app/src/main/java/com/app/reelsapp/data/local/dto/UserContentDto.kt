package com.app.reelsapp.data.local.dto

data class UserContentDto(
    val id: Int,
    val user_id: Int,
    val username: String,
    val title: String,
    val content: String,
    val rating: Int,
    val created_at: String,
    val product_id: Int,
    val image_url: String
)