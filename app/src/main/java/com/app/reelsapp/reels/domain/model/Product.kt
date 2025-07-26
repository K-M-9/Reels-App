package com.app.reelsapp.reels.domain.model

data class Product(
    val id: Int,
    val name: String,
    val description: String,
    val isFavorite:Boolean,
    val price: Double,
    val videoUrl: String
)
