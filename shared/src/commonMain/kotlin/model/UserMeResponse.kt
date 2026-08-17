package model

import kotlinx.serialization.Serializable

@Serializable
data class UserMeResponse(
    val userId: Int?,
    val email: String?,
    val passwordHash: String?
)