package com.app.reelsapp.reels.data.repository

import androidx.paging.PagingData
import com.app.reelsapp.reels.domain.model.Product
import com.app.reelsapp.reels.domain.model.ProductOwner
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    fun getProducts(): Flow<PagingData<Product>>
    fun getProductOwner(): Flow<PagingData<ProductOwner>>
}