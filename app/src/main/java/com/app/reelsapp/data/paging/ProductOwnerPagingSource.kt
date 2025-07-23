package com.app.reelsapp.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.app.reelsapp.data.LocalDataSource
import com.app.reelsapp.data.mapper.toDomain
import com.app.reelsapp.domain.model.ProductOwner
import jakarta.inject.Inject

class ProductOwnerPagingSource @Inject constructor(
    private val localDataSource: LocalDataSource
) : PagingSource<Int, ProductOwner>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ProductOwner> {
        return try {
            val page = params.key ?: 1
            val productOwner = localDataSource.getProductOwner(page, params.loadSize)
            val follows = localDataSource.getUserProductOwnerFollow("1")
            LoadResult.Page(
                data = productOwner.map { it.toDomain(follows.contains(it.id.toString())) },
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (productOwner.isEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ProductOwner>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}
