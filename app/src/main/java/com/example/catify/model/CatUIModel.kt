package com.example.catify.model

import androidx.compose.runtime.Stable

@Stable
data class CatUIModel(
    val id: String,
    val size: ImageSize,
    val imageUrl: String
)

@Stable
data class ImageSize(
    val width: Int,
    val height: Int
)
