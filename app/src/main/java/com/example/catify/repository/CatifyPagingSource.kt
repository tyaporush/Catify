package com.example.catify.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.catify.model.CatItem
import com.example.catify.network.ApiResult
import com.example.catify.repository.remote.CatRemoteDataSource
import retrofit2.HttpException
import java.io.IOException
import java.lang.Exception

class CatifyPagingSource(
    private val catRemoteDataSource: CatRemoteDataSource
) : PagingSource<Int, CatItem>() {


    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CatItem> {
        return try {
            val currentPage = params.key ?: DEFAULT_PAGE
            val cats = catRemoteDataSource.getCats(
                limit = params.loadSize,
                pageNumber = currentPage
            )
            when(cats){
                is ApiResult.ApiError -> {
                    LoadResult.Error(Exception("Api Failure Please Retry -> code: ${cats.code}"))
                }
                is ApiResult.ApiSuccess -> {
                    LoadResult.Page(
                        data = cats.data,
                        prevKey = if (currentPage == 0) null else currentPage - 1,
                        nextKey = if (cats.data.isEmpty()) null else currentPage + 1
                    )
                }
            }

        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, CatItem>): Int? {
        return state.anchorPosition
    }

    companion object {
        private const val DEFAULT_PAGE = 0
    }


}