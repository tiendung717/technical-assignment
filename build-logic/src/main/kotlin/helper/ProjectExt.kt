package helper

import com.android.build.gradle.BaseExtension
import org.gradle.api.Project

fun Project.configureAndroid(configure: BaseExtension.() -> Unit) {
    project.extensions.configure<BaseExtension>("android", configure)
}
