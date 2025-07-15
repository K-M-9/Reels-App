package com.app.reelsapp.presentation.component

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun FooterVideo(
    modifier: Modifier = Modifier,
    name: String,
    description: String
) {
    Column(
        modifier = modifier
            .padding(20.dp)
            .clip(RoundedCornerShape(12.dp))
            .fillMaxWidth()
            .background(Color.White)
    ) {

        Row(
            modifier = Modifier
                .padding(5.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Icon(
                imageVector = Icons.Default.AccountBox,
                contentDescription = null,
            )

            Text(
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center,
                text = name,
                fontWeight = FontWeight.Bold,
                fontSize = 25.sp,
            )
        }
        Text(
            modifier = Modifier.padding(5.dp).fillMaxWidth(),
            text = description,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
        )
    }
}
