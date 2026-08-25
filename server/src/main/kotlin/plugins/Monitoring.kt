package com.reza.plugins

import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.calllogging.CallLogging
import io.ktor.server.plugins.origin
import io.ktor.server.request.httpMethod
import io.ktor.server.request.uri
import org.slf4j.event.Level

fun Application.configureMonitoring() {
    install(CallLogging) {
        level = Level.INFO

        // Formats each incoming call line in stdout
        format { call ->
            val status = call.response.status()
            val httpMethod = call.request.httpMethod.value
            val uri = call.request.uri
            val userAgent = call.request.headers["User-Agent"] ?: "Unknown"
            val remoteHost = call.request.origin.remoteHost

            "[$remoteHost] HTTP $httpMethod $uri -> Status: $status | User-Agent: $userAgent"
        }
    }
}