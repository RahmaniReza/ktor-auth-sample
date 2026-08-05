package com.reza.domain.repository

import model.UserProfileResponse


interface UserRepository {
    suspend fun createUser(email: String, passwordHash: String): UserProfileResponse?
    suspend fun findByEmail(email: String): UserProfileResponse?
}