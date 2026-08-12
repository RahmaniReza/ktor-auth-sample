import androidx.compose.runtime.remember
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.reza.di.initKoin
import com.reza.domain.repository.AuthRepository
import com.reza.ui.AuthScreen
import com.reza.ui.AuthViewModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject


class AppContext : KoinComponent {
    val authRepository: AuthRepository by inject()
}

class App : KoinComponent {
    val authRepository: AuthRepository by inject()

    suspend fun run() {
        val result = authRepository.login("user@example.com", "password123")
        result.onSuccess {
            println("Logged in! Token: ${it.token}")
        }.onFailure {
            println("Error: ${it.message}")
        }
    }
}

fun main(): Unit = application {
    initKoin()
    val app = AppContext()
    val viewModel = remember { AuthViewModel(app.authRepository) }

    Window(
        onCloseRequest = ::exitApplication,
        title = "Auth Client",
        state = rememberWindowState(width = 450.dp, height = 550.dp)
    ) {
        AuthScreen(viewModel = viewModel)
    }
}