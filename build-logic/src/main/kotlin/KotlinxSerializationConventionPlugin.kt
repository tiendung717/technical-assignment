import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.getByType

class KotlinxSerializationConventionPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        val libs = project.extensions.getByType<VersionCatalogsExtension>().named("libs")

        // Apply the Kotlin Serialization plugin
        project.pluginManager.apply("org.jetbrains.kotlin.plugin.serialization")

        // Add the Kotlin Serialization JSON dependency
        project.dependencies {
            add("implementation", libs.findLibrary("kotlinx-serialization-json").get())
            add("implementation", libs.findLibrary("kotlinx-datetime").get())
        }

        // Log a message indicating that the plugin has been applied
        project.logger.lifecycle("✅ KotlinSerializationJsonConventionPlugin: Kotlin Serialization JSON included")

    }
}
