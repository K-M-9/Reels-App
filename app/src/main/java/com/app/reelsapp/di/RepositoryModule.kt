package com.app.reelsapp.di


import android.content.Context
import com.app.reelsapp.authentication.data.repository.AuthenticationRepositoryImpl
import com.app.reelsapp.authentication.domain.AuthenticationRepository
import com.app.reelsapp.core.data.local.CurrentUserPreferences
import com.app.reelsapp.core.data.local.database.dao.UserDao
import com.app.reelsapp.core.data.repository.UserRepositoryImpl
import com.app.reelsapp.core.domain.repository.UserRepository
import com.app.reelsapp.reels.data.LocalDataSource
import com.app.reelsapp.reels.data.local.LocalDataSourceImpl
import com.app.reelsapp.reels.data.local.database.dao.ProductDao
import com.app.reelsapp.reels.data.repository.ProductRepositoryImpl
import com.app.reelsapp.reels.domain.repository.ProductRepository
import com.app.reelsapp.authentication.data.LocalDataSource as AuthLocalDataSource
import com.app.reelsapp.authentication.data.local.LocalDataSourceImpl as AuthLocalDataSourceImpl
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
    fun provideReelsLocalDataSource(
        @ApplicationContext context: Context,
        productDao: ProductDao,
        userDao: UserDao,
        currentUserPreferences: CurrentUserPreferences
    ): LocalDataSource = LocalDataSourceImpl(
        context = context,
        productDao = productDao,
        userDao = userDao,
        currentUserPreferences = currentUserPreferences
    )

    @Provides
    @Singleton
    fun provideAuthLocalDataSource(
        userDao: UserDao,
        currentUserPreferences: CurrentUserPreferences
    ): AuthLocalDataSource = AuthLocalDataSourceImpl(
        userDao = userDao,
        currentUserPreferences = currentUserPreferences
    )

    @Provides
    @Singleton
    fun provideProductRepository(
        localDataSource: LocalDataSource
    ): ProductRepository = ProductRepositoryImpl(localDataSource)

    @Provides
    @Singleton
    fun provideAuthenticationRepository(
        localDataSource: AuthLocalDataSource
    ): AuthenticationRepository = AuthenticationRepositoryImpl(localDataSource)

    @Provides
    @Singleton
    fun provideUserRepository(
        localDataSource: LocalDataSource
    ): UserRepository = UserRepositoryImpl(localDataSource)
}
