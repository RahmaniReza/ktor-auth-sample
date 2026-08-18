import androidx.compose.ui.window.ComposeUIViewController
import com.reza.di.initKoin
import com.reza.ui.App

fun MainViewController() = ComposeUIViewController {
    initKoin()
    App()
}