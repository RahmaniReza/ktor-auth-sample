package com.reza.plugins

import com.reza.domain.repository.UserRepository
import com.reza.routes.authRoutes
import io.ktor.server.application.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import kotlin.getValue

fun Application.configureRouting() {
    val userRepository: UserRepository by inject()
    val jwtService: JwtService by inject()

    routing {
        authRoutes(
            userRepository = userRepository,
            jwtService = jwtService,
        )
    }
}