plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.android.library)
}

group = "com.reza"
version = "1.0.0-SNAPSHOT"

kotlin {
    jvm()

    @Suppress("DEPRECATION")
    androidTarget()

    iosX64()
    iosArm64()
    iosSimulatorArm64()

    js { browser() }
    @OptIn(org.jetbrains.kotlin.gradle.ExperimentalWasmDsl::class)
    wasmJs {
        browser()
    }

    jvmToolchain(21)

    sourceSets {
        commonMain.dependencies {
            implementation(ktorLibs.serialization.kotlinx.json)
        }

        commonTest.dependencies {
            implementation(kotlin("test"))
        }
    }
}

android {
    namespace = "com.reza.shared"
    compileSdk = 35

    defaultConfig {
        minSdk = 24
    }
}