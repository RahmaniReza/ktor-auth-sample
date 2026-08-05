plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlin.serialization)
}

group = "org.reza"
version = "unspecified"

repositories {
    mavenCentral()
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