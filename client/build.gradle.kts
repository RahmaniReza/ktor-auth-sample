plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.compose.multiplatform)
    alias(libs.plugins.android.application)
}

kotlin {
    // --- Target Platforms ---
    @Suppress("DEPRECATION")
    androidTarget()
    jvm()

    // iOS Targets
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    js {
        browser()
    }
    @OptIn(org.jetbrains.kotlin.gradle.ExperimentalWasmDsl::class)
    wasmJs {
        browser()
    }

    sourceSets {
        commonMain.dependencies {
            implementation(project(":shared"))

            // Compose UI
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)

            // Ktor Client
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.contentNegotiation)
            implementation(libs.ktor.serialization.json)
            implementation(libs.ktor.client.logging)

            // Logging plugin
            implementation(ktorLibs.client.logging)
            implementation(libs.logback.classic)

            // Coroutines & DI
            implementation(libs.coroutine)
            implementation(libs.koin.core)
            implementation(libs.koin.compose)
        }

        // --- Platform-Specific Ktor Engines & UI ---

        androidMain.dependencies {
            implementation(libs.ktor.client.okhttp)
            implementation(libs.koin.android)
            implementation(libs.androidx.activity.compose)
        }

        iosMain.dependencies {
            implementation(libs.ktor.client.darwin) // Native iOS Ktor engine
        }

        jvmMain.dependencies {
            // Desktop Engine & JVM Logging
            implementation(compose.desktop.currentOs)
            implementation(libs.ktor.client.cio)
            implementation(libs.logback.classic)
            implementation(libs.coroutine.swing)
        }

        jsMain.dependencies {
            // JS uses the browser fetch engine
            implementation(ktorLibs.client.js)
        }
    }
}

// Android Config Block
android {
    namespace = "com.reza.authclient"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.reza.authclient"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}
