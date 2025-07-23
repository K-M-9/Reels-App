package com.app.reelsapp.reels.di

import android.content.Context
import com.app.reelsapp.reels.data.LocalDataSource
import com.app.reelsapp.reels.data.local.LocalDataSourceImpl
import com.app.reelsapp.reels.data.local.database.dao.ProductDao
import com.app.reelsapp.reels.data.local.database.dao.UserDao
import com.app.reelsapp.reels.data.repository.ProductRepositoryImpl
import com.app.reelsapp.reels.data.repository.UserRepositoryImpl
import com.app.reelsapp.reels.domain.repository.ProductRepository
import com.app.reelsapp.reels.domain.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun providesLocalDataSource(
        @ApplicationContext context: Context,
        productDao: ProductDao,
        userDao: UserDao
    ): LocalDataSource = LocalDataSourceImpl(context, productDao, userDao)

    @Provides
    @Singleton
    fun providesProductRepository(
        localDataSource: LocalDataSource
    ): ProductRepository = ProductRepositoryImpl(localDataSource)

    @Provides
    @Singleton
    fun providesUserRepository(
        localDataSource: LocalDataSource
    ): UserRepository = UserRepositoryImpl(localDataSource)
}