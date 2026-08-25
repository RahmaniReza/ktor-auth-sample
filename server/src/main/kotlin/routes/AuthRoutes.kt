package com.reza.routes

import com.reza.domain.repository.UserRepository
import com.reza.plugins.JwtService
import io.ktor.http.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import model.AuthRequest
import model.AuthResponse
import model.ErrorResponse
import model.UserProfileResponse

fun Route.authRoutes(
    userRepository: UserRepository,
    jwtService: JwtService
) {
    route("/api/v1") {
        post("/register") {
            val request = call.receive<AuthRequest>()

            application.environment.log.info("Register request: $request")

            try {
                // Check for existing user before database insert
                val existingUser = userRepository.findByEmail(request.email)
                if (existingUser != null) {
                    call.respond(HttpStatusCode.Conflict, ErrorResponse("Email is already registered"))
                    return@post
                }

                // Hash password here before passing to repository (e.g., using BCrypt/Argon2)
                val createdUser = userRepository.createUser(request.email, request.password)
                val userId = createdUser?.userId

                if (createdUser != null && userId != null) {
                    val token = jwtService.generateToken(userId, createdUser.email)
                    call.respond(HttpStatusCode.Created, AuthResponse(token))
                } else {
                    call.respond(HttpStatusCode.BadRequest, ErrorResponse("User registration failed"))
                }
            } catch (e: Exception) {
                e.printStackTrace() // Print server-side crash stacktrace to server console
                call.respond(HttpStatusCode.InternalServerError, ErrorResponse(e.localizedMessage ?: "Database error"))
            }
        }

        post("/login") {
            val request = call.receive<AuthRequest>()

            application.environment.log.info("Login request: $request")

            val user = userRepository.findByEmail(request.email)

            if (user == null || user.passwordHash != request.password) {
                call.respond(HttpStatusCode.Unauthorized, ErrorResponse("Invalid email or password"))
                return@post
            }

            val userId = user.userId ?: run {
                call.respond(HttpStatusCode.InternalServerError, ErrorResponse("User ID missing"))
                return@post
            }

            val token = jwtService.generateToken(userId, user.email)
            call.respond(HttpStatusCode.OK, AuthResponse(token))
        }

        // Authenticated Route Scope
        authenticate("auth-jwt") {
            get("/me") {
                val principal = call.principal<JWTPrincipal>()
                val userId = principal?.payload?.getClaim("userId")?.asInt() ?: return@get call.respond(HttpStatusCode.Unauthorized, "Invalid token claims")
                val email = principal.payload.getClaim("email")?.asString() ?: return@get call.respond(HttpStatusCode.Unauthorized, "Invalid token claims")

                val user = userRepository.findByEmail(email)
                call.respond(HttpStatusCode.OK,
                    UserProfileResponse(userId = userId, email = email, passwordHash = user?.passwordHash)
                )
            }
        }
    }
}