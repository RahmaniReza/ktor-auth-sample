package com.reza.di

import io.ktor.client.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import org.koin.dsl.module

expect val platformLogger: Logger

val networkModule = module {
    single {
        HttpClient {
            expectSuccess = true // Throws ResponseException on non-2xx statuses

            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                })
            }
            install(Logging) {
                logger = platformLogger
                level = LogLevel.BODY
            }
        }
    }
}