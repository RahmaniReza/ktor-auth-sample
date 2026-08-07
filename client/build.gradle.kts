
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

            // Ktor Client Core
            implementation(ktorLibs.client.core)

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

        jvmMain.dependencies {
            // CIO goes HERE for JVM
            implementation(ktorLibs.client.cio)
        }

        jsMain.dependencies {
            // JS uses the browser fetch engine
            implementation(ktorLibs.client.js)
        }
    }
}
