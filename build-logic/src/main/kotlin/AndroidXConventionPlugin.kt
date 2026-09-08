import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.getByType

class AndroidXConventionPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        project.pluginManager.withPlugin("com.android.library") {
            addDependencies(project)
        }

        project.pluginManager.withPlugin("com.android.application") {
            addDependencies(project)
        }

        project.logger.lifecycle("✅ AndroidXConventionPlugin: AndroidX dependencies added")

    }

    private fun addDependencies(project: Project) {
        val libs = project.extensions.getByType<VersionCatalogsExtension>().named("libs")

        project.dependencies {
            add("implementation", libs.findLibrary("androidx-core-ktx").get())
            add("implementation", libs.findLibrary("androidx-datastore-preferences").get())
            add("implementation", libs.findLibrary("androidx-splasscreen").get())
            add("implementation", libs.findLibrary("androidx-workmanager").get())
            add("implementation", libs.findLibrary("androidx-material").get())
        }
    }
}
