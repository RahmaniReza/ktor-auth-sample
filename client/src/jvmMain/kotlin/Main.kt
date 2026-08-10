import com.reza.di.initKoin
import com.reza.domain.repository.AuthRepository
import kotlinx.coroutines.runBlocking
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlin.getValue

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

fun main() = runBlocking {
    initKoin()
    val app = App()
    app.run()
}