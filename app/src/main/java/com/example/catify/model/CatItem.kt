package com.example.catify.model

import androidx.annotation.Keep
import com.squareup.moshi.Json


@Keep
data class CatItem(
    @Json(name = "id")
    val id: String,
    @Json(name = "url")
    val imageUrl: String,
    @Json(name = "width")
    val width: String,
    @Json(name = "height")
    val height: String
)
