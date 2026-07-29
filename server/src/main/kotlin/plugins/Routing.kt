package com.reza.plugins

import com.reza.routes.authRoutes
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        authRoutes()
    }
}