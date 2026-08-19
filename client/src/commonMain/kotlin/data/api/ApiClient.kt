package com.reza.data.api

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import model.AuthRequest
import model.AuthResponse
import model.UserProfileResponse

expect val defaultBaseUrl: String

class ApiClient(
    private val client: HttpClient,
    private val baseUrl: String = defaultBaseUrl
) {

    suspend fun register(request: AuthRequest): Result<AuthResponse> = runCatching {
        client.post("$baseUrl/register") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }.body<AuthResponse>()
    }

    suspend fun login(request: AuthRequest): Result<AuthResponse> = runCatching {
        client.post("$baseUrl/login") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }.body<AuthResponse>()
    }

    suspend fun getProfile(token: String): Result<UserProfileResponse> = runCatching {
        client.get("$baseUrl/me") {
            header(HttpHeaders.Authorization, "Bearer $token")
        }.body<UserProfileResponse>()
    }
}