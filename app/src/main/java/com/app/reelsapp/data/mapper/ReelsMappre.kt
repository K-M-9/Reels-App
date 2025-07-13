package com.app.reelsapp.data.mapper

import com.app.reelsapp.data.local.dto.ProductDto
import com.app.reelsapp.data.local.dto.UserContentDto
import com.app.reelsapp.domain.model.Product
import com.app.reelsapp.domain.model.UserContent

fun ProductDto.toDomain(): Product = Product(
    id = id,
    name = name,
    description = description,
    price = price,
    videoUrl = video_url
)

fun UserContentDto.toDomain(): UserContent = UserContent(
    id = id,
    userId = user_id,
    username = username,
    title = title,
    content = content,
    rating = rating,
    createdAt = created_at,
    productId = product_id,
    imageUrl = image_url
)
