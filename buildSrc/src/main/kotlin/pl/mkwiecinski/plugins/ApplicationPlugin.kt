package pl.mkwiecinski.plugins

import org.gradle.api.Plugin
import org.gradle.api.Project
import pl.mkwiecinski.plugins.internal.applyDagger
import pl.mkwiecinski.plugins.internal.applyDaggerAndroid
import pl.mkwiecinski.plugins.internal.applyKotlinStdLib
import pl.mkwiecinski.plugins.internal.configureAndroidExtension
import pl.mkwiecinski.plugins.internal.configureRepositories
import pl.mkwiecinski.plugins.internal.configureUnitTest

class ApplicationPlugin : Plugin<Project> {

    override fun apply(project: Project) = with(project) {
        pluginManager.apply("com.android.application")
        pluginManager.apply("kotlin-android")
        pluginManager.apply("kotlin-kapt")
        pluginManager.apply(QualityPlugin::class.java)

        configureAndroidExtension()

        configureRepositories()

        applyKotlinStdLib()
        applyDagger()
        applyDaggerAndroid()

        configureUnitTest()
    }
}
