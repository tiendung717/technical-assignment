plugins {
    alias(libs.plugins.android.library)
    id("plugin.module")
    id("compose.convention.plugin")
}

android {
    namespace = "com.android.designsystem"
}
