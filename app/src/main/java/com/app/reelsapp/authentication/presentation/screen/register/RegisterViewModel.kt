package com.app.reelsapp.authentication.presentation.screen.register

import android.util.Log
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
class RegisterViewModel @Inject constructor(
    private val authenticationRepository: AuthenticationRepository
) : ViewModel() {

    private val _effect = Channel<RegisterEffect>()
    val effect = _effect.receiveAsFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading

    private val _username = MutableStateFlow("")
    val username = _username

    private val _name = MutableStateFlow("")
    val name = _name

    fun onUsernameChanged(newText: String) {
        _username.value = newText
    }

    fun onNameChanged(newText: String) {
        _name.value = newText
    }

    fun register() {
        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.value = true
            val result = authenticationRepository.register(_username.value, _name.value)
            result.onSuccess { isRegistered ->
                if (isRegistered) {
                    _effect.send(RegisterEffect.NavigateToHomeScreen)
                }
            }.onFailure {
                _effect.send(RegisterEffect.ShowError("An error occurred during registration"))
            }
            _isLoading.value = false
        }
    }
}