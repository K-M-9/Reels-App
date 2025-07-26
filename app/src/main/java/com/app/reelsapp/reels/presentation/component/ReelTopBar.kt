package com.app.reelsapp.reels.presentation.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.app.reelsapp.R

@Composable
fun ReelTopBar(
    modifier: Modifier = Modifier,
    name: String,
    imageUrl: String,
    isFollow: Boolean,
    onFollowClick: ( isFollow: Boolean) -> Unit
) {


    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        UserInfo(
            userName = name,
            userImageUrl = imageUrl,
            modifier = Modifier.weight(1f)
        )

        FollowButton(
            onClick = {
                onFollowClick(!isFollow)
            },
            showButton = !isFollow,
            modifier = Modifier.padding(start = 16.dp)
        )
    }
}

@Composable
private fun UserInfo(
    userName: String,
    userImageUrl: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        UserAvatar(
            imageUrl = userImageUrl,
            contentDescription = stringResource(R.string.owner_image)
        )

        Text(
            text = userName,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
private fun UserAvatar(
    imageUrl: String,
    contentDescription: String,
    modifier: Modifier = Modifier
) {
    var isImageFailed by remember { mutableStateOf(false) }

    val painter = rememberAsyncImagePainter(
        model = imageUrl.ifEmpty { R.drawable.ic_launcher_foreground },
        onError = { isImageFailed = true },
        onSuccess = { isImageFailed = false }
    )

    val isLoading = painter.state is AsyncImagePainter.State.Loading

    Box(
        modifier = modifier
            .size(50.dp)
            .clip(CircleShape),
        contentAlignment = Alignment.Center
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                color = Color.White,
                modifier = Modifier.size(24.dp),
                strokeWidth = 2.dp
            )
        }

        if (!isImageFailed) {
            Image(
                painter = painter,
                contentDescription = contentDescription,
                contentScale = ContentScale.Crop,
                modifier = Modifier.matchParentSize()
            )
        } else {
            Icon(
                painter = painterResource(id = R.drawable.ic_launcher_foreground),
                contentDescription = "Fallback image",
                tint = Color.Gray,
                modifier = Modifier.matchParentSize()
            )
        }
    }
}


@Composable
private fun FollowButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    showButton: Boolean
) {
    AnimatedVisibility(
        visible = showButton,
        enter = fadeIn() + scaleIn(),
        exit = fadeOut() + scaleOut()
    ) {
        Button(
            onClick = onClick,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White,
            ),
            shape = RoundedCornerShape(15.dp),
            modifier = modifier
        ) {
            Text(
                text = stringResource(R.string.follow),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        }
    }
}