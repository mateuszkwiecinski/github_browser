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
    kotlinExtension.jvmToolchain(25)
}

internal fun Project.setupCommonKotlinVersion() {
    val kotlinVersion = project.getKotlinPluginVersion()

    // ktlint bundles its own kotlin-compiler-embeddable and fails to initialize when forced to a newer Kotlin version
    configurations.matching { it.name != "detekt" && !it.name.startsWith("ktlint") }.configureEach { configuration ->
        configuration.resolutionStrategy.eachDependency {
            if (it.requested.group == "org.jetbrains.kotlin" && it.requested.name.startsWith("kotlin")) {
                it.useVersion(kotlinVersion)
            }
        }
    }
}
