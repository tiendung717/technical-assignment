plugins {
    id("plugin.application")
    id("androidx.convention.plugin")
    id("hilt.convention.plugin")
    id("compose.convention.plugin")
    id("kotlinx.serialization.convention.plugin")
    id("retrofit.convention.plugin")
    alias(libs.plugins.secrets.gradle.plugin)
    alias(libs.plugins.jetbrains.kotlin.android)
}

android {
    namespace = "com.android.app"

    defaultConfig {
        applicationId = "com.android.app"
    }
}

secrets {
    propertiesFileName = "application.properties"
}

dependencies {
    implementation(project(":designsystem"))

    // Timber logging
    implementation(libs.log.timber)
    implementation(libs.androidx.core.ktx)

    // Coil3
    implementation(libs.coil.compose)
    implementation(libs.coil.network.okhttp)

    testImplementation(libs.junit)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.mockk)
}
