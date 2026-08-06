package com.reza.data.repository

import com.reza.data.api.AuthApiClient
import com.reza.domain.repository.AuthRepository
import model.AuthRequest
import model.AuthResponse
import model.UserProfileResponse

class AuthRepositoryImpl(private val api: AuthApiClient) : AuthRepository {
    override suspend fun register(email: String, password: String): Result<AuthResponse> {
        return runCatching {
            api.register(AuthRequest(email, password))
        }
    }

    override suspend fun login(email: String, password: String): Result<AuthResponse> {
        return runCatching {
            api.login(AuthRequest(email, password))
        }
    }

    override suspend fun getProfile(token: String): Result<UserProfileResponse> {
        return runCatching {
            api.getProfile(token)
        }
    }
}