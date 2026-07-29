package com.reza.routes

import com.reza.domain.model.AuthRequest
import com.reza.domain.model.AuthResponse
import com.reza.domain.repository.UserRepository
import com.reza.plugins.JwtService
import io.ktor.http.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.authRoutes() {
    val userRepository: UserRepository by inject()
    val jwtService: JwtService by inject()

    route("/api/v1") {
        post("/register") {
            val request = call.receive<AuthRequest>()

            // Note: Use BCrypt or Argon2 to hash passwords in production
            val createdUser = userRepository.createUser(request.email, request.password)
            val userId = createdUser?.id

            if (createdUser != null && userId != null) {
                val token = jwtService.generateToken(userId, createdUser.email)
                call.respond(HttpStatusCode.Created, AuthResponse(token))
            } else {
                call.respond(HttpStatusCode.BadRequest, "User registration failed")
            }
        }

        post("/login") {
            val request = call.receive<AuthRequest>()
            val user = userRepository.findByEmail(request.email)

            if (user == null || user.passwordHash != request.password) {
                call.respond(HttpStatusCode.Unauthorized, "Invalid email or password")
                return@post
            }

            val userId = user.id ?: run {
                call.respond(HttpStatusCode.InternalServerError, "User ID missing")
                return@post
            }

            val token = jwtService.generateToken(userId, user.email)
            call.respond(HttpStatusCode.OK, AuthResponse(token))
        }

        // Authenticated Route Scope
        authenticate("auth-jwt") {
            get("/me") {
                val principal = call.principal<JWTPrincipal>()
                val userId = principal?.payload?.getClaim("userId")?.asInt()
                val email = principal?.payload?.getClaim("email")?.asString()

                call.respond(HttpStatusCode.OK, mapOf("userId" to userId, "email" to email))
            }
        }
    }
}