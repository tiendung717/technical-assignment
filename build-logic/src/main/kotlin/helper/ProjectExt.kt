package helper

import com.android.build.gradle.BaseExtension
import org.gradle.api.Project

/**
 * Configures the `android` extension of either an application or a library module.
 *
 * Uses [BaseExtension] rather than `AppExtension` so the same convention plugins can be applied to
 * both `com.android.application` and `com.android.library` modules.
 */
fun Project.configureAndroid(configure: BaseExtension.() -> Unit) {
    project.extensions.configure<BaseExtension>("android", configure)
}
