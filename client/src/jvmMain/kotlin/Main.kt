import androidx.compose.runtime.remember
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.reza.di.initKoin
import com.reza.domain.repository.AuthRepository
import com.reza.ui.AppContext
import com.reza.ui.AuthScreen
import com.reza.ui.AuthViewModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

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