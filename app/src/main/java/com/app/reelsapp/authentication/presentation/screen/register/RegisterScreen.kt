package com.app.reelsapp.authentication.presentation.screen.register

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.app.reelsapp.authentication.presentation.component.AuthenticationButton
import com.app.reelsapp.authentication.presentation.component.AuthenticationTextField

@Composable
fun RegisterScreen(
    viewModel: RegisterViewModel = hiltViewModel(),
    navigateToHomeScreen: () -> Unit,
) {

    val effect = viewModel.effect.collectAsStateWithLifecycle(null).value
    val content = LocalContext.current

    LaunchedEffect(effect) {
        when (effect) {
            RegisterEffect.NavigateToHomeScreen -> navigateToHomeScreen()
            null -> {}
            is RegisterEffect.ShowError -> Toast.makeText(content, effect.message, Toast.LENGTH_SHORT).show()
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
            text = viewModel.name.collectAsStateWithLifecycle().value,
            onTextChanged = viewModel::onNameChanged,
            hint = "Name"
        )

        AuthenticationTextField(
            text = viewModel.username.collectAsStateWithLifecycle().value,
            onTextChanged = viewModel::onUsernameChanged,
            hint = "Username"
        )


        AuthenticationButton(
            text = "Register",
            isLoading = viewModel.isLoading.collectAsStateWithLifecycle().value,
            onClick = viewModel::register
        )

    }
}

@Preview
@Composable
fun RegisterScreenPreview() {
    RegisterScreen(
        navigateToHomeScreen = {},
    )
}