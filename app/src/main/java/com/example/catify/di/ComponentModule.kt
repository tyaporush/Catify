package com.example.catify.di

import com.example.catify.repository.CatRepoImpl
import com.example.catify.repository.CatifyRepo
import com.example.catify.repository.remote.CatRemoteDataSource
import com.example.catify.repository.remote.CatRemoteDataSourceImpl
import com.example.catify.usecase.CatUseCaseImpl
import com.example.catify.usecase.CatsUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface ComponentModule {
    @Singleton
    @Binds
    fun getCatsUseCase(catsUseCase: CatUseCaseImpl): CatsUseCase

    @Singleton
    @Binds
    fun getRepository(repoImpl: CatRepoImpl): CatifyRepo

    @Singleton
    @Binds
    fun getDataSource(dataSourceImpl: CatRemoteDataSourceImpl): CatRemoteDataSource
}