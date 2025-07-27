package com.app.reelsapp.authentication.presentation.screen.login

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.app.reelsapp.authentication.presentation.component.AuthenticationButton
import com.app.reelsapp.authentication.presentation.component.AuthenticationTextField

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel(),
    navigateToHomeScreen: () -> Unit,
    navigateToRegisterScreen: () -> Unit
) {
    val effect = viewModel.effect.collectAsStateWithLifecycle(null).value
    val content = LocalContext.current
    LaunchedEffect(effect) {
        when (effect) {
            LoginEffect.NavigateToHomeScreen -> navigateToHomeScreen()
            is LoginEffect.ShowError -> Toast.makeText(content, effect.message, Toast.LENGTH_SHORT).show()
            null -> {}
        }
    }
    Column(
        modifier = Modifier
            .padding(15.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        AuthenticationTextField(
            text = viewModel.username.collectAsStateWithLifecycle().value,
            onTextChanged = viewModel::onUsernameChanged,
            hint = "Username"
        )

        AuthenticationButton(
            text = "Login",
            isLoading = viewModel.isLoading.collectAsStateWithLifecycle().value,
            onClick = viewModel::login
        )

        Text(
            modifier = Modifier.clickable { navigateToRegisterScreen() },
            text = "Register",
            style = TextStyle(
                color = Color.Black,
                fontSize = 25.sp,
                fontWeight = FontWeight.Normal
            )
        )
    }
}

@Preview(showSystemUi = true)
@Composable
fun LoginScreenPreview() {
    LoginScreen(
        navigateToRegisterScreen = {},
        navigateToHomeScreen = {}
    )
}