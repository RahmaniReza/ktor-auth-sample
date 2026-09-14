package com.reza.di

import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.Logger

actual val platformLogger: Logger
    get() = Logger.DEFAULT