package com.app.reelsapp.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.app.reelsapp.data.LocalDataSource
import com.app.reelsapp.data.local.database.dto.ProductDto
import jakarta.inject.Inject


class ProductPagingSource @Inject constructor(
    private val localDataSource: LocalDataSource,
) : PagingSource<Int, ProductDto>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ProductDto> {
        return try {
            val page = params.key ?: 1
            val products = localDataSource.getProducts(page, params.loadSize)

            LoadResult.Page(
                data = products,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (products.isEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ProductDto>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}