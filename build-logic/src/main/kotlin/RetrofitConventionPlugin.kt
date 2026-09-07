import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

/**
 * Wires Retrofit, OkHttp and the kotlinx-serialization converter into any Android application or
 * library module that requests it. Also applies the serialization plugin so response models can be
 * annotated with `@Serializable`.
 */
class RetrofitConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("org.jetbrains.kotlin.plugin.serialization")

            pluginManager.withPlugin("com.android.library") {
                addDependencies()
            }
            pluginManager.withPlugin("com.android.application") {
                addDependencies()
            }

            logger.lifecycle("✅ RetrofitConventionPlugin: Retrofit + OkHttp + kotlinx.serialization applied")
        }
    }

    private fun Project.addDependencies() {
        val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")
        dependencies {
            add("implementation", platform(libs.findLibrary("okhttp-bom").get()))
            add("implementation", libs.findLibrary("okhttp").get())
            add("implementation", libs.findLibrary("okhttp-logging").get())
            add("implementation", libs.findLibrary("retrofit").get())
            add("implementation", libs.findLibrary("retrofit-converter-kotlinx-serialization").get())
            add("implementation", libs.findLibrary("kotlinx-serialization-json").get())
        }
    }
}
