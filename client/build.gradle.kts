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
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true

            // Export module dependencies if classes from :shared need to be visible to Swift
            export(project(":shared"))
        }
    }

    js {
        browser()
    }
    @OptIn(org.jetbrains.kotlin.gradle.ExperimentalWasmDsl::class)
    wasmJs {
        browser()
    }

    sourceSets {
        commonMain.dependencies {
            api(project(":shared"))

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
            implementation(libs.slf4j.android)
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

    packaging {
        resources {
            excludes += "/META-INF/INDEX.LIST"
        }
    }
}
