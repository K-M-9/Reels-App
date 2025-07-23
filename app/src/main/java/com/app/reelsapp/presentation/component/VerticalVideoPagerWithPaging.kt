package com.app.reelsapp.presentation.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import com.app.reelsapp.R
import com.app.reelsapp.domain.model.Product
import com.app.reelsapp.domain.model.ProductOwner

@Composable
fun VerticalVideoPagerWithPaging(
    modifier: Modifier = Modifier,
    productPagingItems: LazyPagingItems<Product>,
    productOwnerPagingItems: LazyPagingItems<ProductOwner>,
    currentReelPlaying: Boolean,
    initialPage: Int = 0,
    onFavorite: (id: String, isFavorite: Boolean) -> Unit,
    onFollow: (id: String, isFollow: Boolean) -> Unit,
    onReelClick: (isPlaying: Boolean) -> Unit
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
        val productOwner = productOwnerPagingItems[pageIndex]

        if (product != null) {

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
                        onReelClick(!player.isPlaying)
                    },
                    onVideoDispose = { onReelClick(false) },
                    onVideoGoBackground = { onReelClick(false) }
                )

                CenterContent(
                    modifier = Modifier.align(Alignment.Center),
                    isFavorite = product.isFavorite,
                    onFavorite = { favorite -> onFavorite(product.id.toString(), favorite) }
                )


                productOwner?.let {
                    ReelTopBar(
                        modifier = Modifier
                            .statusBarsPadding()
                            .padding(10.dp)
                            .fillMaxWidth()
                            .align(Alignment.TopStart),
                        name = productOwner.username,
                        imageUrl = productOwner.imageUrl,
                        isFollow = productOwner.isFollow,
                        onFollowClick = { isFollow ->
                            onFollow(productOwner.id.toString(), isFollow)
                        }
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
                    visible = currentReelPlaying,
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

@Composable
fun CenterContent(
    modifier: Modifier = Modifier,
    isFavorite: Boolean,
    onFavorite: (isFavorite: Boolean) -> Unit
) {
    Column(
        modifier = modifier
            .padding(20.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.End
    ) {
        Icon(
            modifier = Modifier
                .size(50.dp)
                .clickable {
                    onFavorite(!isFavorite)
                },
            painter = if (isFavorite) painterResource(R.drawable.ic_favorite) else painterResource(R.drawable.ic_unfavorite),
            contentDescription = null,
            tint = if (isFavorite) Color.Red else Color.White
        )
    }
}

@Composable
private fun LoadingScreen() {
    Box(
        modifier = Modifier.fillMaxSize().background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(color = Color.White)
    }
}

