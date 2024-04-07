package com.example.catify.network

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.awaitResponse

internal class ApiResponseCallDelegate<T : Any>(
    proxy: Call<T>,
    private val coroutineScope: CoroutineScope,
) : CallDelegate<T, ApiResult<T>>(proxy) {

    override fun enqueueImpl(callback: Callback<ApiResult<T>>) {
        coroutineScope.launch {
            try {
                val response = handleApi { proxy.awaitResponse() }
                callback.onResponse(this@ApiResponseCallDelegate, Response.success(response))
            } catch (e: Exception) {
                callback.onResponse(
                    this@ApiResponseCallDelegate,
                    Response.success(ApiResult.ApiError(-1, e.localizedMessage))
                )
            }
        }
    }

    override fun executeImpl(): Response<ApiResult<T>> =
        runBlocking(coroutineScope.coroutineContext) {
            val response = handleApi { proxy.execute() }
            Response.success(response)
        }

    override fun cloneImpl() = ApiResponseCallDelegate(proxy.clone(), coroutineScope)
}