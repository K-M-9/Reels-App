package com.app.reelsapp.core.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.OptIn
import androidx.media3.common.util.UnstableApi
import com.app.reelsapp.core.presentation.navigation.NavGraph
import com.app.reelsapp.core.presentation.navigation.Routes
import com.app.reelsapp.core.presentation.theme.ReelsAppTheme
import com.app.reelsapp.reels.presentation.component.CacheManager
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            ReelsAppTheme {
                NavGraph(Routes.Authentication)
            }
        }
    }

    @OptIn(UnstableApi::class)
    override fun onStop() {
        super.onStop()
        CacheManager.release()
    }

    @OptIn(UnstableApi::class)
    override fun onDestroy() {
        super.onDestroy()
        CacheManager.clearCache(this)
    }
}

