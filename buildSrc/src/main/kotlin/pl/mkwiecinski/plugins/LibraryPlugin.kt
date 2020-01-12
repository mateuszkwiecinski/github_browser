package pl.mkwiecinski.plugins

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.plugin.KaptExtension
import pl.mkwiecinski.plugins.internal.applyDagger
import pl.mkwiecinski.plugins.internal.applyDaggerAndroid
import pl.mkwiecinski.plugins.internal.configureUnitTest

class LibraryPlugin : Plugin<Project> {

    override fun apply(project: Project) = with(project) {
        pluginManager.apply("com.starter.library.android")

        improveDatabindingLogs()

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
}
