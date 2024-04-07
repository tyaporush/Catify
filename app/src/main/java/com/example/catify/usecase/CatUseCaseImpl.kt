package com.example.catify.usecase

import androidx.paging.PagingData
import com.example.catify.model.CatItem
import com.example.catify.repository.CatifyRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CatUseCaseImpl @Inject constructor(
    private val repository: CatifyRepo
): CatsUseCase {
    override suspend fun invoke(): Flow<PagingData<CatItem>> {
        return repository.getCatImages()
    }
}