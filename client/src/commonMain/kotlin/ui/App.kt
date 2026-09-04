package com.reza.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.reza.domain.repository.AuthRepository
import org.koin.compose.koinInject
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlin.getValue

class AppContext : KoinComponent {
    val authRepository: AuthRepository by inject()
}

@Composable
fun App() {
    val authRepository: AuthRepository = koinInject()
    val viewModel = remember { AuthViewModel(authRepository) }

    MaterialTheme {
        AuthScreen(viewModel = viewModel)
    }
}