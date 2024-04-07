package com.example.catify.repository

import androidx.paging.PagingData
import com.example.catify.model.CatItem
import kotlinx.coroutines.flow.Flow

interface CatifyRepo {

    fun getCatImages(): Flow<PagingData<CatItem>>
}