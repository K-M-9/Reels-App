package com.app.reelsapp.domain.repository

import androidx.paging.PagingData
import com.app.reelsapp.domain.model.Product
import com.app.reelsapp.domain.model.UserContent
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    fun getProducts(): Flow<PagingData<Product>>
    fun getUserContent(): Flow<PagingData<UserContent>>
}