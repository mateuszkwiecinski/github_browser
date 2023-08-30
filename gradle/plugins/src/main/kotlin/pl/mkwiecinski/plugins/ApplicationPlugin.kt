package pl.mkwiecinski.plugins

import org.gradle.api.Plugin
import org.gradle.api.Project
import pl.mkwiecinski.plugins.internal.applyDagger
import pl.mkwiecinski.plugins.internal.applyDaggerAndroid
import pl.mkwiecinski.plugins.internal.configureUnitTest
import pl.mkwiecinski.plugins.internal.setupCommonKotlinVersion

class ApplicationPlugin : Plugin<Project> {

    override fun apply(project: Project): Unit = with(project) {
        pluginManager.apply("com.starter.application.android")

        setupCommonKotlinVersion()
        applyDaggerAndroid()

        configureUnitTest()
    }
}
