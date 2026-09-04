package com.reza.di

import io.ktor.client.plugins.logging.Logger

actual val platformLogger: Logger = object : Logger {
    override fun log(message: String) {
        println("[Ktor] $message")
    }
}