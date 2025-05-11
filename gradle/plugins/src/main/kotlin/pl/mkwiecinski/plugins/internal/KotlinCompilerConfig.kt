package pl.mkwiecinski.plugins.internal

import com.apollographql.apollo3.gradle.api.kotlinProjectExtension
import com.apollographql.apollo3.gradle.api.kotlinProjectExtensionOrThrow
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.jvm.toolchain.JavaLanguageVersion
import org.jetbrains.kotlin.gradle.dsl.HasConfigurableKotlinCompilerOptions
import org.jetbrains.kotlin.gradle.plugin.getKotlinPluginVersion

internal fun Project.configureCompilerFlags() {
    (kotlinProjectExtension as HasConfigurableKotlinCompilerOptions<*>).apply {
        compilerOptions {
            freeCompilerArgs.add("-Xjvm-default=all")
        }
    }
    kotlinProjectExtensionOrThrow.jvmToolchain(24)
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
