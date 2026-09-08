import com.android.build.gradle.AppExtension
import configure.ProjectConfig
import org.gradle.api.GradleException
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.io.FileInputStream
import java.util.Properties

class ApplicationConfigPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        applyPlugins(project)
        setJvmTarget(project)
        setVersionConfig(project)
        setSigningConfig(project)
    }

    private fun setJvmTarget(project: Project) {
        project.plugins.withId("com.android.application") {
            project.extensions.configure<AppExtension>("android") {
                compileOptions {
                    sourceCompatibility = JavaVersion.VERSION_21
                    targetCompatibility = JavaVersion.VERSION_21
                }
            }
        }

        project.tasks.withType<KotlinCompile>().configureEach {
            compilerOptions.jvmTarget.set(JvmTarget.JVM_21)
        }
    }

    private fun applyPlugins(project: Project) {
        with(project.pluginManager) {
            apply("com.android.application")
        }
    }

    private fun setSigningConfig(project: Project) {
        project.plugins.withId("com.android.application") {
            val props = Properties()
            val keystoreFile = project.rootProject.file("keystore.properties")
            if (keystoreFile.exists()) {
                props.load(FileInputStream(keystoreFile))
            } else {
                throw GradleException("Missing keystore.properties file!")
            }

            project.extensions.configure<AppExtension>("android") {
                signingConfigs {
                    create("sign_debug") {
                        storeFile = project.rootProject.file(props["storeFile"].toString())
                        storePassword = props["storePassword"].toString()
                        keyAlias = props["keyAlias"].toString()
                        keyPassword = props["keyPassword"].toString()
                    }

                    create("sign_release") {
                        storeFile = project.rootProject.file(props["storeFile"].toString())
                        storePassword = props["storePassword"].toString()
                        keyAlias = props["keyAlias"].toString()
                        keyPassword = props["keyPassword"].toString()
                    }
                }

                buildTypes {
                    getByName("debug") {
                        signingConfig = signingConfigs.getByName("sign_debug")
                    }

                    getByName("release") {
                        signingConfig = signingConfigs.getByName("sign_release")
                        isMinifyEnabled = true
                        proguardFiles(
                            getDefaultProguardFile("proguard-android-optimize.txt"),
                            "proguard-rules.pro"
                        )
                    }
                }

                project.logger.lifecycle("🔐 Signing configs applied from keystore.properties")
            }
        }
    }

    private fun setVersionConfig(project: Project) {
        project.plugins.withId("com.android.application") {
            val props = Properties()
            val versionFile = project.rootProject.file("version.properties")

            if (versionFile.exists()) {
                props.load(FileInputStream(versionFile))
            } else {
                throw GradleException("Missing version.properties file!")
            }

            val major = props["VERSION_MAJOR"].toString().toInt()
            val minor = props["VERSION_MINOR"].toString().toInt()
            val patch = props["VERSION_PATCH"].toString().toInt()

            val versionCode = major * 10000 + minor * 100 + patch
            val versionName = "$major.$minor.$patch"

            project.extensions.configure<AppExtension>("android") {
                setCompileSdkVersion(ProjectConfig.COMPILE_SDK)
                buildFeatures.buildConfig = true
                defaultConfig.apply {
                    minSdk = ProjectConfig.MIN_SDK
                    targetSdk = ProjectConfig.TARGET_SDK
                    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

                    this.versionName = versionName
                    this.versionCode = versionCode
                }
            }

            project.logger.lifecycle("📦 Applied versioning: versionCode=$versionCode, versionName=$versionName")
        }
    }

}
