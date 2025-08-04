package com.app.reelsapp.di


import android.content.Context
import com.app.reelsapp.authentication.data.repository.AuthenticationRepositoryImpl
import com.app.reelsapp.authentication.domain.AuthenticationRepository
import com.app.reelsapp.core.data.local.CurrentUserPreferences
import com.app.reelsapp.core.data.local.database.dao.UserDao
import com.app.reelsapp.core.data.repository.UserRepositoryImpl
import com.app.reelsapp.core.domain.repository.UserRepository
import com.app.reelsapp.reels.data.ReelsLocalDataSource
import com.app.reelsapp.reels.data.local.ReelsLocalDataSourceImpl
import com.app.reelsapp.reels.data.local.database.dao.ProductDao
import com.app.reelsapp.reels.data.repository.ProductRepositoryImpl
import com.app.reelsapp.reels.domain.repository.ProductRepository
import com.app.reelsapp.authentication.data.AuthenticationLocalDataSource
import com.app.reelsapp.authentication.data.local.AuthenticationLocalDataSourceImpl
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
    ): ReelsLocalDataSource = ReelsLocalDataSourceImpl(
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
    ): AuthenticationLocalDataSource = AuthenticationLocalDataSourceImpl(
        userDao = userDao,
        currentUserPreferences = currentUserPreferences
    )

    @Provides
    @Singleton
    fun provideProductRepository(
        reelsLocalDataSource: ReelsLocalDataSource
    ): ProductRepository = ProductRepositoryImpl(reelsLocalDataSource)

    @Provides
    @Singleton
    fun provideAuthenticationRepository(
        localDataSource:  AuthenticationLocalDataSource
    ): AuthenticationRepository = AuthenticationRepositoryImpl(localDataSource)

    @Provides
    @Singleton
    fun provideUserRepository(
        reelsLocalDataSource: ReelsLocalDataSource
    ): UserRepository = UserRepositoryImpl(reelsLocalDataSource)
}
