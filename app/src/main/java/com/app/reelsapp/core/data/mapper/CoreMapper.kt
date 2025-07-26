package com.app.reelsapp.core.data.mapper

import com.app.reelsapp.core.data.local.database.dto.UserDto
import com.app.reelsapp.core.domain.model.User

fun UserDto.toDomain(): User = User(
    name = name,
    username = username,
)
