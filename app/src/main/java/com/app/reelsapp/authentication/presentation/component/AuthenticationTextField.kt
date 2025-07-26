package com.app.reelsapp.authentication.presentation.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.reelsapp.R

@Composable
fun AuthenticationTextField(
    text: String,
    onTextChanged: (String) -> Unit,
    modifier: Modifier = Modifier,
    hint: String,
    isEnabled: Boolean = true,
    keyboardType: KeyboardType = KeyboardType.Text,
    leadingIconRes: Int? = R.drawable.ic_person
) {
    Column(modifier = modifier) {
        BasicTextField(
            value = text,
            onValueChange = onTextChanged,
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            textStyle = TextStyle(
                color = Color.Black,
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal
            ),
            enabled = isEnabled,
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            cursorBrush = SolidColor(Color.Black),
            decorationBox = { innerTextField ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    leadingIconRes?.let {
                        Icon(
                            painter = painterResource(id = it),
                            contentDescription = null,
                            modifier = Modifier
                                .padding(end = 8.dp)
                                .size(24.dp),
                            tint = Color.Black
                        )
                    }
                    Box(modifier = Modifier.weight(1f)) {
                        if (text.isEmpty()) {
                            Text(
                                text = hint,
                                style = TextStyle(
                                    color = Color.Black.copy(alpha = 0.7f),
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Normal
                                )
                            )
                        }
                        innerTextField()
                    }
                }
            }
        )

        HorizontalDivider(
            modifier = Modifier
                .padding(top = 5.dp)
                .fillMaxWidth(),
            thickness = 1.5.dp,
            color = Color.Black
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TextFieldLoginPreview() {
    AuthenticationTextField(
        text = "",
        onTextChanged = {},
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        hint = "Username",
        leadingIconRes = R.drawable.ic_person
    )
}
