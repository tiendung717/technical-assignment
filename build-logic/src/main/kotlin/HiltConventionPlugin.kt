import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

class HiltConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.google.dagger.hilt.android")
                apply("com.google.devtools.ksp")
            }

            pluginManager.withPlugin("com.android.library") {
                addDependencies()
            }
            pluginManager.withPlugin("com.android.application") {
                addDependencies()
            }

            logger.lifecycle("✅ HiltConventionPlugin: Hilt + KSP setup applied")
        }
    }

    private fun Project.addDependencies() {
        val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")
        dependencies {
            add("implementation", libs.findLibrary("hilt-android").get())
            add("implementation", libs.findLibrary("androidx-hilt-navigation-compose").get())
            add("ksp", libs.findLibrary("hilt-compiler").get())
        }
    }
}
