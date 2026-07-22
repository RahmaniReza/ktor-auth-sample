package com.reza.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: Int? = null,
    val email: String,
    val passwordHash: String
)

@Serializable
data class AuthResponse(val token: String)

@Serializable
data class AuthRequest(val email: String, val password: String)