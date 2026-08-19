package com.reza

import com.reza.plugins.*
import io.ktor.server.application.*
import io.ktor.server.netty.*

fun main(args: Array<String>) {
    EngineMain.main(args)
}

fun Application.module() {
    configureMonitoring()
    configureKoin()
    configureSerialization()
    configureDatabase()
    configureSecurity()
    configureRouting()
}