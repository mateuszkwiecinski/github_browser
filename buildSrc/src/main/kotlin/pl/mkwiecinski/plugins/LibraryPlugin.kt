package pl.mkwiecinski.plugins

import com.android.build.gradle.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.plugin.KaptExtension
import pl.mkwiecinski.plugins.internal.applyDagger
import pl.mkwiecinski.plugins.internal.applyDaggerAndroid
import pl.mkwiecinski.plugins.internal.applyKotlinStdLib
import pl.mkwiecinski.plugins.internal.configureAndroidExtension
import pl.mkwiecinski.plugins.internal.configureRepositories
import pl.mkwiecinski.plugins.internal.configureUnitTest

class LibraryPlugin : Plugin<Project> {

    override fun apply(project: Project) = with(project) {
        pluginManager.apply("com.android.library")
        pluginManager.apply("kotlin-android")
        pluginManager.apply("kotlin-kapt")
        pluginManager.apply("quality")

        improveDatabindingLogs()
        disableBuildConfigGeneration()
        configureAndroidExtension()
        configureRepositories()

        applyKotlinStdLib()
        applyDagger()
        applyDaggerAndroid()

        configureUnitTest()
    }

    private fun Project.improveDatabindingLogs() {
        extensions.configure(KaptExtension::class.java) {
            it.javacOptions {
                option("-Xmaxerrs", 1000)
            }
        }
    }

    private fun Project.disableBuildConfigGeneration() {
        project.extensions.getByType(LibraryExtension::class.java).apply {
            libraryVariants.all { variant ->
                variant.generateBuildConfigProvider.configure { it.enabled = false }
            }
        }
    }
}
