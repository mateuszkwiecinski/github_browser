package pl.mkwiecinski.plugins

import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.plugin.KaptExtension
import pl.mkwiecinski.plugins.internal.applyDagger
import pl.mkwiecinski.plugins.internal.applyDaggerAndroid
import pl.mkwiecinski.plugins.internal.configureCompilerFlags
import pl.mkwiecinski.plugins.internal.configureUnitTest
import pl.mkwiecinski.plugins.internal.setupCommonKotlinVersion

class LibraryPlugin : Plugin<Project> {

    override fun apply(project: Project) = with(project) {
        pluginManager.apply("com.starter.library.android")

        setupCommonKotlinVersion()
        applyDaggerAndroid()
        extensions.getByType(LibraryExtension::class.java).apply {
            namespace = "pl.mkwiecinski${path.replace(":", ".").replace("-", "_")}"
        }

        improveDatabindingLogs()

        configureUnitTest()
        configureCompilerFlags()
    }

    private fun Project.improveDatabindingLogs() {
        extensions.configure(KaptExtension::class.java) {
            it.javacOptions {
                option("-Xmaxerrs", MAX_KAPT_ERRORS)
            }
        }
    }

    companion object {
        private const val MAX_KAPT_ERRORS = 1000
    }
}
