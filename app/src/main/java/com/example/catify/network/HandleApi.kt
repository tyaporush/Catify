package com.example.catify.network

import retrofit2.HttpException
import retrofit2.Response

suspend fun <T : Any> handleApi(
    execute: suspend () -> Response<T>
): ApiResult<T> {
    return try {
        val response = execute()
        val body = response.body()
        if (response.isSuccessful && body != null) {
            ApiResult.ApiSuccess(body)
        } else {
            ApiResult.ApiError(code = response.code(), message = response.message())
        }
    } catch (e: HttpException) {
        ApiResult.ApiError(code = e.code(), message = e.message())
    } catch (e: Throwable) {
        ApiResult.ApiError(-1, e.localizedMessage)
    }
}