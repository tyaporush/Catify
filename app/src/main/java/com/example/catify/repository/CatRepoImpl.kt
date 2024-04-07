package com.example.catify.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.catify.model.CatItem
import com.example.catify.repository.remote.CatRemoteDataSource
import com.example.catify.utility.Constants
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CatRepoImpl @Inject constructor(
    private val dataSource: CatRemoteDataSource
) : CatifyRepo {

    override fun getCatImages(): Flow<PagingData<CatItem>> {
        return Pager(
            config = PagingConfig(pageSize = Constants.MAX_PAGE_SIZE, prefetchDistance = 5),
            pagingSourceFactory = {
                CatifyPagingSource(dataSource)
            }
        ).flow
    }

}