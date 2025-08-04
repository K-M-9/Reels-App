package com.app.reelsapp.reels.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.app.reelsapp.reels.data.ReelsLocalDataSource
import com.app.reelsapp.reels.data.local.database.dto.ProductOwnerDto
import jakarta.inject.Inject

class ProductOwnerPagingSource @Inject constructor(
    private val reelsLocalDataSource: ReelsLocalDataSource,
) : PagingSource<Int, ProductOwnerDto>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ProductOwnerDto> {
        return try {
            val page = params.key ?: 1
            val productOwner = reelsLocalDataSource.getProductOwner(page, params.loadSize)

            LoadResult.Page(
                data = productOwner,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (productOwner.isEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ProductOwnerDto>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}
