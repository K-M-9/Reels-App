package com.app.reelsapp.authentication.presentation.screen.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.reelsapp.authentication.domain.AuthenticationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authenticationRepository: AuthenticationRepository,
) : ViewModel() {


    private val _effect = Channel<LoginEffect>()
    val effect = _effect.receiveAsFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading

    private val _username = MutableStateFlow("")
    val username = _username


    fun login() {
        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.value = true
            if (authenticationRepository.login(_username.value))
                _effect.send(LoginEffect.NavigateToHomeScreen)
            else
                _effect.send(LoginEffect.ShowError("Username is wrong"))
            _isLoading.value = false
        }
    }

    fun onUsernameChanged(newText: String) {
        _username.value = newText
    }


}