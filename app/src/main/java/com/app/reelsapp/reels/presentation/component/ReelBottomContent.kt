package com.app.reelsapp.reels.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.reelsapp.R

@Composable
fun ReelBottomContent(
    modifier: Modifier = Modifier,
    productName: String,
    productDescription: String
) {
    Column(
        modifier = modifier
            .padding(20.dp)
            .clip(RoundedCornerShape(12.dp))
            .fillMaxWidth()
            .background(Color.White.copy(alpha = 0.95f))
    ) {
        ProductHeader(
            name = productName,
            modifier = Modifier.padding(vertical = 8.dp, horizontal = 12.dp)
        )

        ProductDescription(
            description = productDescription,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
        )
    }
}

@Composable
private fun ProductHeader(
    name: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Icon(
            imageVector = Icons.Default.AccountBox,
            contentDescription = stringResource(R.string.product_icon),
            tint = Color.Black
        )

        Text(
            text = name,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            color = Color.Black,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun ProductDescription(
    description: String,
    modifier: Modifier = Modifier
) {
    Text(
        modifier = modifier.fillMaxWidth(),
        text = description,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        color = Color.Black.copy(alpha = 0.8f),
        maxLines = 3,
        overflow = TextOverflow.Ellipsis
    )
}