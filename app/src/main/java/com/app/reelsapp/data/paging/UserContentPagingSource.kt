package com.app.reelsapp.data.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.app.reelsapp.data.LocalDataSource
import com.app.reelsapp.data.mapper.toDomain
import com.app.reelsapp.domain.model.UserContent

class UserContentPagingSource(
    private val localDataSource: LocalDataSource
) : PagingSource<Int, UserContent>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UserContent> {
        return try {
            val page = params.key ?: 1
            val userContent = localDataSource.getUserContent(page, params.loadSize)
            Log.d("sssssssss","${userContent.size}")

            LoadResult.Page(
                data = userContent.map { it.toDomain() },
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (userContent.isEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, UserContent>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}
