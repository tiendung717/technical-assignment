import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `kotlin-dsl`
}

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

dependencies {
    implementation(libs.android.gradle.plugin)
    implementation(libs.kotlin.gradle.plugin)
    implementation(libs.compose.compiler.gradle.plugin)
    implementation(libs.spotless.gradle.plugin)
}

gradlePlugin {
    plugins {
        create("module") {
            id = "plugin.module"
            implementationClass = "DefaultModuleConfigPlugin"
        }

        create("application") {
            id = "plugin.application"
            implementationClass = "ApplicationConfigPlugin"
        }

        create("compose") {
            id = "compose.convention.plugin"
            implementationClass = "ComposeConventionPlugin"
        }

        create("kotlinx-serialization") {
            id = "kotlinx.serialization.convention.plugin"
            implementationClass = "KotlinxSerializationConventionPlugin"
        }

        create("androidx") {
            id = "androidx.convention.plugin"
            implementationClass = "AndroidXConventionPlugin"
        }

        create("hilt") {
            id = "hilt.convention.plugin"
            implementationClass = "HiltConventionPlugin"
        }

        create("retrofit") {
            id = "retrofit.convention.plugin"
            implementationClass = "RetrofitConventionPlugin"
        }
    }
}

val compileKotlin: KotlinCompile by tasks
compileKotlin.compilerOptions {
    jvmTarget.set(JvmTarget.JVM_21)
}