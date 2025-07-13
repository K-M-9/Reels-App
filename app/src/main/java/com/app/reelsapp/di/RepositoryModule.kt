package com.app.reelsapp.di

import com.app.reelsapp.data.LocalDataSource
import com.app.reelsapp.data.local.LocalDataSourceImpl
import com.app.reelsapp.data.repository.ProductRepositoryImpl
import com.app.reelsapp.domain.repository.ProductRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindLocalDataSource(
        localDataSourceImpl: LocalDataSourceImpl
    ): LocalDataSource

    @Binds
    abstract fun bindProductRepository(
        productRepositoryImpl: ProductRepositoryImpl
    ): ProductRepository
}
