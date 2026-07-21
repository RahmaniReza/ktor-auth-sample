
plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(ktorLibs.plugins.ktor)
    alias(libs.plugins.kotlin.serialization)
}


application {
    mainClass = "io.ktor.server.netty.EngineMain"
}

kotlin {
    jvmToolchain(21)
}
dependencies {
    // --- Serialization & Formatting ---
    implementation(ktorLibs.serialization.kotlinx.json)

    // --- Security & Authentication ---
    implementation(ktorLibs.server.auth)
    implementation(ktorLibs.server.auth.jwt)

    // --- Configuration & Server Engine ---
    implementation(ktorLibs.server.config.yaml)
    implementation(ktorLibs.server.contentNegotiation)
    implementation(ktorLibs.server.core)
    implementation(ktorLibs.server.netty)
    implementation(ktorLibs.server.resources)

    // --- Logging ---
    implementation(libs.logback.classic)

    // --- Dependency Injection ---
    implementation(libs.koin.ktor)

    // --- Database & ORM ---
    implementation(libs.exposed.core)
    implementation(libs.exposed.jdbc)
    implementation(libs.postgresql)
    implementation(libs.hikaricp)

    // --- Testing ---
    testImplementation(kotlin("test"))
    testImplementation(ktorLibs.server.testHost)
}
