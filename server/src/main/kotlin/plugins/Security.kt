package com.reza.plugins

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*
import org.koin.ktor.ext.inject
import java.util.Date

class JwtService(environment: ApplicationEnvironment) {
    val secret = environment.config.property("jwt.secret").getString()
    val issuer = environment.config.property("jwt.issuer").getString()
    val audience = environment.config.property("jwt.audience").getString()
    val realm = environment.config.property("jwt.realm").getString()

    fun generateToken(userId: Int, email: String): String {
        return JWT.create()
            .withAudience(audience)
            .withIssuer(issuer)
            .withClaim("userId", userId)
            .withClaim("email", email)
            .withExpiresAt(Date(System.currentTimeMillis() + 86_400_000)) // 24 hours
            .sign(Algorithm.HMAC256(secret))
    }
}

fun Application.configureSecurity() {
    val jwtService: JwtService by inject()

    install(Authentication) {
        jwt("auth-jwt") {
            realm = jwtService.realm
            verifier(
                JWT.require(Algorithm.HMAC256(jwtService.secret))
                    .withAudience(jwtService.audience)
                    .withIssuer(jwtService.issuer)
                    .build()
            )
            validate { credential ->
                val userId = credential.payload.getClaim("userId").asInt()
                if (userId != null) {
                    JWTPrincipal(credential.payload)
                } else null
            }
            challenge { _, _ ->
                call.respond(HttpStatusCode.Unauthorized, "Token is invalid or expired")
            }
        }
    }
}