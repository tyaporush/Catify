package com.example.catify.repository.remote

import com.example.catify.model.CatItem
import com.example.catify.network.ApiResult
import com.example.catify.services.ApiService
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class CatRemoteDataSourceImpl @Inject constructor(
    private val apiService: ApiService
) : CatRemoteDataSource {
    override suspend fun getCats(
        limit: Int,
        pageNumber: Int
    ): ApiResult<List<CatItem>> {
        return apiService.getImages(
            limit = limit,
            pageNumber = pageNumber
        )
    }
}