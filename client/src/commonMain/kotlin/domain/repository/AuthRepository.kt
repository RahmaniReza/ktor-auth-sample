package com.reza.domain.repository

import model.AuthResponse
import model.UserProfileResponse

interface AuthRepository {
    suspend fun register(email: String, password: String): Result<AuthResponse>
    suspend fun login(email: String, password: String): Result<AuthResponse>
    suspend fun getProfile(token: String): Result<UserProfileResponse>
}