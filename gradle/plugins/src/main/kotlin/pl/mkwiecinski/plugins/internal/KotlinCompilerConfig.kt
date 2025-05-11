package pl.mkwiecinski.plugins.internal

import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.dsl.HasConfigurableKotlinCompilerOptions
import org.jetbrains.kotlin.gradle.dsl.kotlinExtension
import org.jetbrains.kotlin.gradle.plugin.getKotlinPluginVersion

internal fun Project.configureCompilerFlags() {
    (kotlinExtension as HasConfigurableKotlinCompilerOptions<*>).apply {
        compilerOptions {
            freeCompilerArgs.add("-Xjvm-default=all")
        }
    }
    kotlinExtension.jvmToolchain(24)
}

internal fun Project.setupCommonKotlinVersion() {
    val kotlinVersion = project.getKotlinPluginVersion()

    configurations.matching { it.name != "detekt" }.configureEach { configuration ->
        configuration.resolutionStrategy.eachDependency {
            if (it.requested.group == "org.jetbrains.kotlin" && it.requested.name.startsWith("kotlin")) {
                it.useVersion(kotlinVersion)
            }
        }
    }
}
