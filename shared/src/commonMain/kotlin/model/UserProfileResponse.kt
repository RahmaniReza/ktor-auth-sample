package model

import kotlinx.serialization.Serializable

@Serializable
data class UserProfileResponse(
    val userId: Int?,
    val email: String,
    val passwordHash: String
)