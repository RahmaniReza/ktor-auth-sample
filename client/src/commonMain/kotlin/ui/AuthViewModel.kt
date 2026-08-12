package com.reza.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.reza.domain.repository.AuthRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AuthViewModel(private val repository: AuthRepository) {
    private val scope = CoroutineScope(Dispatchers.Main)

    var email by mutableStateOf("user@example.com")
    var password by mutableStateOf("password123")

    var token by mutableStateOf<String?>(null)
    var statusMessage by mutableStateOf("")
    var isLoading by mutableStateOf(false)

    fun register() {
        scope.launch {
            isLoading = true
            statusMessage = "Registering..."
            repository.register(email, password)
                .onSuccess {
                    token = it.token
                    statusMessage = "Registered & Logged in successfully!"
                }
                .onFailure {
                    statusMessage = "Registration failed: ${it.message}"
                }
            isLoading = false
        }
    }

    fun login() {
        scope.launch {
            isLoading = true
            statusMessage = "Logging in..."
            repository.login(email, password)
                .onSuccess {
                    token = it.token
                    statusMessage = "Logged in successfully!"
                }
                .onFailure {
                    statusMessage = "Login failed: ${it.message}"
                }
            isLoading = false
        }
    }

    fun fetchProfile() {
        val currentToken = token ?: run {
            statusMessage = "No active token. Please login first."
            return
        }

        scope.launch {
            isLoading = true
            statusMessage = "Fetching profile..."
            repository.getProfile(currentToken)
                .onSuccess { profile ->
                    statusMessage = "Profile -> ID: ${profile.userId}, Email: ${profile.email}"
                }
                .onFailure {
                    statusMessage = "Fetch failed: ${it.message}"
                }
            isLoading = false
        }
    }
}