package com.example.catify.repository.remote

import com.example.catify.model.CatItem
import com.example.catify.network.ApiResult

interface CatRemoteDataSource {

    suspend fun getCats(
        limit: Int,
        pageNumber: Int
    ): ApiResult<List<CatItem>>
}