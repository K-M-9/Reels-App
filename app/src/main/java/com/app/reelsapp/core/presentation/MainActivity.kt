package com.app.reelsapp.core.presentation

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.OptIn
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.fragment.app.FragmentActivity
import androidx.media3.common.util.UnstableApi
import com.app.reelsapp.core.presentation.theme.ReelsAppTheme
import com.app.reelsapp.reels.presentation.component.CacheManager
import com.app.reelsapp.reels.presentation.component.ReelBottomContent
import com.app.reelsapp.reels.presentation.component.ReelTopBar
import com.zegocloud.uikit.prebuilt.livestreaming.ZegoUIKitPrebuiltLiveStreamingConfig
import com.zegocloud.uikit.prebuilt.livestreaming.ZegoUIKitPrebuiltLiveStreamingFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : FragmentActivity() {

    private val userID = "audo"
    private val username = "audo"
    private val isHost = false
    private val liveID = "123"
    private val appID = 407143386L
    private val appSign = "a6acca0ecd9decf8febb32b1ea5cc23790d8f9c6d0394dca3fa15c5734fd751d"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            ReelsAppTheme {
                val config = if (isHost) {
                    ZegoUIKitPrebuiltLiveStreamingConfig.host(true)
                } else {
                    ZegoUIKitPrebuiltLiveStreamingConfig.audience(true)
                }

                val fragmentManager = remember {
                    this@MainActivity.supportFragmentManager
                }

                val fragment = remember {
                    ZegoUIKitPrebuiltLiveStreamingFragment.newInstance(
                        appID, appSign, userID, username, liveID, config
                    )
                }


                Box(
                    modifier = Modifier.fillMaxSize()
                ) {
                    AndroidView(
                        modifier = Modifier.fillMaxSize(),
                        factory = { context ->
                            FrameLayout(context).apply {
                                id = View.generateViewId()
                                layoutParams = ViewGroup.LayoutParams(
                                    ViewGroup.LayoutParams.MATCH_PARENT,
                                    ViewGroup.LayoutParams.MATCH_PARENT,
                                )
                            }
                        },
                        update = {
                            fragmentManager.beginTransaction().replace(it.id, fragment).commit()
                        }
                    )

                    ReelTopBar(
                        modifier = Modifier
                            .padding(vertical = 50.dp, horizontal = 20.dp)
                            .fillMaxWidth()
                            .align(Alignment.TopCenter),
                        name = "Product Name",
                        imageUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRuNhTZJTtkR6b-ADMhmzPvVwaLuLdz273wvQ&s",
                        isFollow = true,
                    ) {}

                    ReelBottomContent(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(bottom = 40.dp),
                        productName = "Product Name",
                        productDescription = "Product Description"
                    )

                }
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