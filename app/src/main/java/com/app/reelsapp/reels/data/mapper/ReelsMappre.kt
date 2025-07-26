package com.app.reelsapp.reels.data.mapper

import com.app.reelsapp.reels.data.local.database.dto.ProductDto
import com.app.reelsapp.reels.data.local.database.dto.ProductOwnerDto
import com.app.reelsapp.reels.domain.model.Product
import com.app.reelsapp.reels.domain.model.ProductOwner

fun ProductDto.toDomain(isFavorite: Boolean): Product = Product(
    id = id,
    name = name,
    description = description,
    price = price,
    isFavorite = isFavorite,
    videoUrl = video_url
)

fun ProductOwnerDto.toDomain(isFollow: Boolean): ProductOwner = ProductOwner(
    id = id,
    userId = user_id,
    username = name,
    title = title,
    content = content,
    isFollow = isFollow,
    rating = rating,
    createdAt = created_at,
    productId = product_id,
    imageUrl = image_url
)