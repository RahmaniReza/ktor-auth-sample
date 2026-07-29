package com.reza.plugins

import com.reza.data.repository.UserRepositoryImpl
import com.reza.domain.repository.UserRepository
import io.ktor.server.application.Application
import io.ktor.server.application.install
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin

val appModule = module {
    single<UserRepository> { UserRepositoryImpl() }
    single { JwtService(get()) }
}

fun Application.configureKoin() {
    install(Koin) {
        modules(appModule)
    }
}