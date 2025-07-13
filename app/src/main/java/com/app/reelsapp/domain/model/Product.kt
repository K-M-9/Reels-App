package com.app.reelsapp.domain.model

data class Product(
    val id: Int,
    val name: String,
    val description: String,
    val price: Double,
    val videoUrl: String
)
