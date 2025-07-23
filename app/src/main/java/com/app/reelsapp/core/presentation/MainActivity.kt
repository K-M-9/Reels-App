package com.app.reelsapp.core.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.app.reelsapp.reels.presentation.screen.ReelsScreen
import com.app.reelsapp.core.presentation.theme.ReelsAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            ReelsAppTheme {
                ReelsScreen()
            }
        }
    }
}

