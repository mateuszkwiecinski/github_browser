package pl.mkwiecinski.plugins

import org.gradle.api.Plugin
import org.gradle.api.Project
import pl.mkwiecinski.plugins.internal.applyDagger
import pl.mkwiecinski.plugins.internal.applyKotlinStdLib
import pl.mkwiecinski.plugins.internal.configureRepositories
import pl.mkwiecinski.plugins.internal.configureUnitTest

class KotlinPlugin : Plugin<Project> {

    override fun apply(project: Project) = with(project) {
        pluginManager.apply("kotlin")
        pluginManager.apply("kotlin-kapt")
        pluginManager.apply("quality")

        configureRepositories()

        applyKotlinStdLib()
        applyDagger()

        configureUnitTest()
    }
}
