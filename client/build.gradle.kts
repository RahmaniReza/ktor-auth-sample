
plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlin.serialization)
}


kotlin {
    jvm()
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

            implementation(ktorLibs.client.core)

            // Ktor Client Core & Engine (CIO Engine for JVM/Coroutines)
            implementation(ktorLibs.client.core)
            implementation(ktorLibs.client.cio)

            // Content Negotiation & JSON Serialization
            implementation(ktorLibs.client.contentNegotiation)
            implementation(ktorLibs.serialization.kotlinx.json)

            // Logging plugin
            implementation(ktorLibs.client.logging)

            // Kotlinx Coroutines
            implementation(libs.coroutine)

            // Koin for Kotlin Multiplatform
            implementation(libs.koin.core)

        }

    }
}
