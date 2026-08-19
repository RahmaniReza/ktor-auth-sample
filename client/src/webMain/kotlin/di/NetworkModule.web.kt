package com.reza.di

import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.Logger

actual val platformLogger: io.ktor.client.plugins.logging.Logger
    get() = Logger.DEFAULT