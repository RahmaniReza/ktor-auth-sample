package com.reza.domain.repository

import com.reza.domain.model.User

interface UserRepository {
    suspend fun createUser(email: String, passwordHash: String): User?
    suspend fun findByEmail(email: String): User?
}