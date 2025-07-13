package com.app.reelsapp.domain.model

data class UserContent(
    val id: Int,
    val userId: Int,
    val username: String,
    val title: String,
    val content: String,
    val rating: Int,
    val createdAt: String,
    val productId: Int,
    val imageUrl: String
)