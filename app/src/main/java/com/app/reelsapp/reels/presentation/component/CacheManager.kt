package com.app.reelsapp.reels.presentation.component

import android.content.Context
import androidx.media3.common.util.UnstableApi
import androidx.media3.database.StandaloneDatabaseProvider
import androidx.media3.datasource.cache.LeastRecentlyUsedCacheEvictor
import androidx.media3.datasource.cache.SimpleCache
import java.io.File

@UnstableApi
object CacheManager {
    @Volatile
    private var simpleCache: SimpleCache? = null

    fun getCache(context: Context): SimpleCache {
        return simpleCache ?: synchronized(this) {
            simpleCache ?: createCache(context).also { simpleCache = it }
        }
    }

    private fun createCache(context: Context): SimpleCache {
        val cacheDir = File(context.cacheDir, "media_cache")
        val cacheSize = 100L * 1024L * 1024L // 100 MB
        val evictor = LeastRecentlyUsedCacheEvictor(cacheSize)
        val databaseProvider = StandaloneDatabaseProvider(context)

        return SimpleCache(cacheDir, evictor, databaseProvider)
    }

    fun release() {
        simpleCache?.release()
        simpleCache = null
    }

    fun clearCache(context: Context) {
        release()
        val cacheDir = File(context.cacheDir, "media_cache")
        if (cacheDir.exists()) {
            cacheDir.deleteRecursively()
        }
    }
}
