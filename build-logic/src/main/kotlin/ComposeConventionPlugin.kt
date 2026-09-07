import helper.configureAndroid
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.getByType

class ComposeConventionPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        with(project.pluginManager) {
            apply("org.jetbrains.kotlin.plugin.compose")
        }

        project.plugins.withId("com.android.library") {
            addDependencies(project)
        }

        project.plugins.withId("com.android.application") {
            addDependencies(project)
        }

        project.configureAndroid {
            buildFeatures.apply {
                compose = true
            }
        }
    }

    private fun addDependencies(project: Project) {
        val libs = project.extensions.getByType<VersionCatalogsExtension>().named("libs")

        project.dependencies {
            add("implementation", platform(libs.findLibrary("compose-bom").get()))
            add("implementation", libs.findLibrary("compose-foundation").get())
            add("implementation", libs.findLibrary("compose-material3").get())
            add("implementation", libs.findLibrary("compose-material-icons-extended").get())
            add("implementation", libs.findLibrary("compose-runtime").get())
            add("implementation", libs.findLibrary("compose-ui").get())
            add("implementation", libs.findLibrary("compose-ui-tooling-preview").get())
            add("implementation", libs.findLibrary("compose-google-fonts").get())
            add("implementation", libs.findLibrary("compose-lifecycle-viewmodel").get())
            add("implementation", libs.findLibrary("compose-lifecycle-runtime").get())
            add("implementation", libs.findLibrary("compose-activity").get())
            add("implementation", libs.findLibrary("compose-navigation").get())

            add("debugImplementation", libs.findLibrary("compose-ui-tooling").get())
        }

        project.logger.lifecycle("✅ ComposeConventionPlugin: Compose dependencies added")
    }
}
