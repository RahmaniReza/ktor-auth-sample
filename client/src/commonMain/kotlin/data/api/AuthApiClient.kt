package com.reza.data.api

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import model.AuthRequest
import model.AuthResponse
import model.UserProfileResponse

class AuthApiClient(
    private val client: HttpClient,
    private val baseUrl: String = "http://localhost:8080/api/v1"
) {

    suspend fun register(request: AuthRequest): AuthResponse {
        return client.post("$baseUrl/register") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }.body()
    }

    suspend fun login(request: AuthRequest): AuthResponse {
        return client.post("$baseUrl/login") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }.body()
    }

    suspend fun getProfile(token: String): UserProfileResponse {
        return client.get("$baseUrl/me") {
            header(HttpHeaders.Authorization, "Bearer $token")
        }.body()
    }
}