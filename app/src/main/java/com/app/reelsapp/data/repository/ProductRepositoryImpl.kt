package com.app.reelsapp.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.app.reelsapp.data.LocalDataSource
import com.app.reelsapp.data.mapper.toDomain
import com.app.reelsapp.data.paging.ProductOwnerPagingSource
import com.app.reelsapp.data.paging.ProductPagingSource
import com.app.reelsapp.domain.model.Product
import com.app.reelsapp.domain.model.ProductOwner
import com.app.reelsapp.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ProductRepositoryImpl(
    private val localDataSource: LocalDataSource
) : ProductRepository {

    override fun getProducts(): Flow<PagingData<Product>> {

        return Pager(
            config = PagingConfig(
                pageSize = 5,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { ProductPagingSource(localDataSource) }
        ).flow.map { pagingData ->
            val currentUsername = localDataSource.getCurrentUsername() ?: ""
            val favoriteDTOs = localDataSource.getUserProductFavorite(currentUsername)
            pagingData.map { it.toDomain(favoriteDTOs.contains(it.id.toString())) }
        }
    }

    override fun getProductOwner(): Flow<PagingData<ProductOwner>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { ProductOwnerPagingSource(localDataSource) }
        ).flow.map { pagingData ->
            val currentUsername = localDataSource.getCurrentUsername() ?: ""
            val followDTOs = localDataSource.getUserProductOwnerFollow(currentUsername)
            pagingData.map { it.toDomain(followDTOs.contains(it.id.toString())) }
        }
    }
}