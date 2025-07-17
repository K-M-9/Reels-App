package com.app.reelsapp.presentation.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import com.app.reelsapp.domain.model.Product
import com.app.reelsapp.domain.model.UserContent

@Composable
fun VerticalVideoPagerWithPaging(
    modifier: Modifier = Modifier,
    productPagingItems: LazyPagingItems<Product>,
    userContentPagingItems: LazyPagingItems<UserContent>,
    initialPage: Int = 0,
) {
    val pagerState = rememberPagerState(
        initialPage = initialPage,
        pageCount = { productPagingItems.itemCount }
    )

    VerticalPager(
        state = pagerState,
        modifier = modifier.fillMaxSize()
    ) { pageIndex ->
        val product = productPagingItems[pageIndex]
        val userContent = userContentPagingItems[pageIndex]

        if (product != null) {
            var pauseButtonVisibility by remember { mutableStateOf(false) }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black)
            ) {

                VideoPlayer(
                    videoUrl = product.videoUrl,
                    pagerState = pagerState,
                    pageIndex = pageIndex,
                    onSingleTap = { player ->
                        pauseButtonVisibility = !player.isPlaying
                    },
                    onVideoDispose = { pauseButtonVisibility = false },
                    onVideoGoBackground = { pauseButtonVisibility = false }
                )

                userContent?.let {
                    ReelTopBar(
                        modifier = Modifier
                            .statusBarsPadding()
                            .padding(10.dp)
                            .fillMaxWidth()
                            .align(Alignment.TopStart),
                        name = userContent.username,
                        imageUrl = userContent.imageUrl,
                        isFollow = true,
                        onFollowClick = {}
                    )
                }

                ReelBottomContent(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 20.dp),
                    productName = product.name,
                    productDescription = product.description
                )

                AnimatedVisibility(
                    visible = pauseButtonVisibility,
                    enter = scaleIn(spring(Spring.DampingRatioMediumBouncy), initialScale = 1.5f),
                    exit = scaleOut(tween(150)),
                    modifier = Modifier.align(Alignment.Center)
                ) {
                    Icon(
                        imageVector = Icons.Default.PlayArrow,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier
                            .size(80.dp)
                            .background(Color.Black.copy(alpha = 0.5f), CircleShape)
                            .padding(16.dp)
                    )
                }
            }
        } else {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    color = Color.White
                )
            }
        }
    }
}
