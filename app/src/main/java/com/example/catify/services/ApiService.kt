package com.example.catify.services

import com.example.catify.model.CatItem
import com.example.catify.network.ApiResult
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("images/search")
    suspend fun getImages(
        @Query("limit") limit: Int,
        @Query("page") pageNumber: Int
    ): ApiResult<List<CatItem>>
}