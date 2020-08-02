package pl.mkwiecinski.plugins

import org.gradle.api.Plugin
import org.gradle.api.Project
import pl.mkwiecinski.plugins.internal.applyDagger
import pl.mkwiecinski.plugins.internal.configureCompilerFlags
import pl.mkwiecinski.plugins.internal.configureUnitTest

class KotlinPlugin : Plugin<Project> {

    override fun apply(project: Project) = with(project) {
        pluginManager.apply("com.starter.library.kotlin")

        applyDagger()

        configureUnitTest()
        configureCompilerFlags()
    }
}
