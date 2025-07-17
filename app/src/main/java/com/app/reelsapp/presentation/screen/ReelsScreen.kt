package com.app.reelsapp.presentation.screen


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.app.reelsapp.R
import com.app.reelsapp.presentation.component.VerticalVideoPagerWithPaging


@Composable
fun ReelsScreen(
    modifier: Modifier = Modifier,
    reelsViewModel: ReelsViewModel = hiltViewModel()
) {
    val productPagingItems = reelsViewModel.productPagingData.collectAsLazyPagingItems()
    val userContentPagingItems = reelsViewModel.userContentPagingData.collectAsLazyPagingItems()

    Box(
        modifier = modifier
            .background(Color.Black)
            .fillMaxSize()
    ) {
        when {
            productPagingItems.loadState.refresh is LoadState.Loading -> {
                LoadingContent()
            }

            productPagingItems.loadState.refresh is LoadState.Error -> {
                ErrorContent(onRetry = productPagingItems::retry)
            }

            productPagingItems.itemCount > 0 -> {
                VerticalVideoPagerWithPaging(
                    modifier = modifier,
                    productPagingItems = productPagingItems,
                    userContentPagingItems = userContentPagingItems,
                    initialPage = 0,
                    currentReelPlaying = reelsViewModel.currentReelPlaying.collectAsStateWithLifecycle().value,
                    onReelClick = reelsViewModel::onChangeCurrentReelPlaying
                )
            }
        }
    }
}

@Composable
private fun ErrorContent(onRetry: () -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(R.string.failed_to_load_videos),
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            Button(
                onClick = onRetry,
                modifier = Modifier.padding(top = 16.dp)
            ) {
                Text(stringResource(R.string.retry))
            }
        }
    }
}

@Composable
private fun LoadingContent() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            color = Color.White
        )
    }
}
