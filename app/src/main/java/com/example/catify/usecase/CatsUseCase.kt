package com.example.catify.usecase

import androidx.paging.PagingData
import com.example.catify.model.CatItem
import kotlinx.coroutines.flow.Flow

interface CatsUseCase {

    suspend operator fun invoke(): Flow<PagingData<CatItem>>
}