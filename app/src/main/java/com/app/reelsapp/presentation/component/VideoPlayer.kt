package com.app.reelsapp.presentation.component

import androidx.annotation.OptIn
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DefaultDataSource
import androidx.media3.datasource.cache.CacheDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.MediaSource
import androidx.media3.exoplayer.source.ProgressiveMediaSource
import androidx.media3.ui.PlayerView

@OptIn(UnstableApi::class)
@Composable
fun VideoPlayer(
    videoUrl: String,
    pagerState: PagerState,
    pageIndex: Int,
    onSingleTap: (Player) -> Unit,
    onVideoDispose: () -> Unit,
    onVideoGoBackground: () -> Unit
) {
    val context = LocalContext.current
    val currentPage by rememberUpdatedState(pagerState.currentPage)
    val isCurrentPage = currentPage == pageIndex

    val exoPlayer = remember {
        ExoPlayer.Builder(context).build().apply {
            val cache = CacheManager.getCache(context)
            val defaultDataSourceFactory = DefaultDataSource.Factory(context)

            val cacheDataSourceFactory = CacheDataSource.Factory()
                .setCache(cache)
                .setUpstreamDataSourceFactory(defaultDataSourceFactory)
                .setFlags(CacheDataSource.FLAG_IGNORE_CACHE_ON_ERROR)

            val mediaSource: MediaSource = ProgressiveMediaSource.Factory(cacheDataSourceFactory)
                .createMediaSource(MediaItem.fromUri(videoUrl))

            setMediaSource(mediaSource)
            prepare()
            repeatMode = Player.REPEAT_MODE_ONE
        }
    }


    LaunchedEffect(isCurrentPage) {
        if (isCurrentPage) {
            exoPlayer.play()
        } else {
            exoPlayer.pause()
            onVideoGoBackground()
        }
    }

    DisposableEffect(exoPlayer) {
        onDispose {
            exoPlayer.release()
            onVideoDispose()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
                if (exoPlayer.isPlaying) exoPlayer.pause() else exoPlayer.play()
                onSingleTap(exoPlayer)
            }
    ) {
        AndroidView(
            factory = { ctx ->
                PlayerView(ctx).apply {
                    player = exoPlayer
                    setShowBuffering(PlayerView.SHOW_BUFFERING_WHEN_PLAYING)
                    useController = false
                    resizeMode = androidx.media3.ui.AspectRatioFrameLayout.RESIZE_MODE_ZOOM
                }
            },
            modifier = Modifier
                .fillMaxSize()
                .aspectRatio(9f / 16f, matchHeightConstraintsFirst = true)
        )
    }
}