package pl.mkwiecinski.plugins

import org.gradle.api.Plugin
import org.gradle.api.Project
import pl.mkwiecinski.plugins.internal.applyDagger
import pl.mkwiecinski.plugins.internal.applyDaggerAndroid
import pl.mkwiecinski.plugins.internal.configureUnitTest

class ApplicationPlugin : Plugin<Project> {

    override fun apply(project: Project): Unit = with(project) {
        pluginManager.apply("com.starter.application.android")
        pluginManager.apply("com.starter.quality")

        applyDagger()
        applyDaggerAndroid()

        configureUnitTest()
    }
}
