package com.app.reelsapp.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.app.reelsapp.data.LocalDataSource
import com.app.reelsapp.data.paging.ProductPagingSource
import com.app.reelsapp.data.paging.UserContentPagingSource
import com.app.reelsapp.domain.model.Product
import com.app.reelsapp.domain.model.UserContent
import com.app.reelsapp.domain.repository.ProductRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow

class ProductRepositoryImpl @Inject constructor(
    private val localDataSource: LocalDataSource
) : ProductRepository {

    override fun getProducts(): Flow<PagingData<Product>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { ProductPagingSource(localDataSource) }
        ).flow
    }

    override fun getUserContent(): Flow<PagingData<UserContent>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { UserContentPagingSource(localDataSource) }
        ).flow
    }
}