import configure.ProjectConfig
import helper.configureAndroid
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

class DefaultModuleConfigPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        applyPlugins(project)
        setProjectConfig(project)
        setJvmTarget(project)
    }

    private fun setJvmTarget(project: Project) {
        project.tasks.withType<KotlinCompile>().configureEach {
            compilerOptions.jvmTarget.set(JvmTarget.JVM_21)
        }
    }

    private fun applyPlugins(project: Project) {
        with(project.pluginManager) {
            apply("org.jetbrains.kotlin.android")
            apply("org.jetbrains.kotlin.plugin.parcelize")
            apply("com.google.devtools.ksp")
        }
    }

    private fun setProjectConfig(project: Project) {
        project.configureAndroid {
            setCompileSdkVersion(ProjectConfig.COMPILE_SDK)

            defaultConfig {
                minSdk = ProjectConfig.MIN_SDK
                targetSdk = ProjectConfig.TARGET_SDK
                testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
            }

            compileOptions {
                sourceCompatibility = JavaVersion.VERSION_21
                targetCompatibility = JavaVersion.VERSION_21
            }

            buildFeatures.apply {
                buildConfig = true
            }
        }
    }
}