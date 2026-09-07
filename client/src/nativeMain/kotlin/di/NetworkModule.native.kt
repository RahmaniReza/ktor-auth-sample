package com.reza.di

actual val platformLogger: Logger = object : Logger {
    override fun log(message: String) {
        println("[Native] $message")
    }
}