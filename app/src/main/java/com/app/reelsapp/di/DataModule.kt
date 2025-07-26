package com.app.reelsapp.di

import android.content.Context
import androidx.room.Room
import com.app.reelsapp.core.data.local.CurrentUserPreferences
import com.app.reelsapp.core.data.local.database.ReelsDataBase
import com.app.reelsapp.core.data.local.database.dao.UserDao
import com.app.reelsapp.reels.data.local.database.dao.ProductDao
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): ReelsDataBase {
        return Room.databaseBuilder(
            context,
            ReelsDataBase::class.java,
            "app_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideCurrentUserPreferences(
        @ApplicationContext context: Context
    ): CurrentUserPreferences =  CurrentUserPreferences(context)

    @Provides
    @Singleton
    fun provideUserDao(database: ReelsDataBase): UserDao = database.userDao()

    @Provides
    @Singleton
    fun provideProductDao(database: ReelsDataBase): ProductDao = database.productDao()

    @Provides
    @Singleton
    fun provideGson(): Gson = Gson()
}