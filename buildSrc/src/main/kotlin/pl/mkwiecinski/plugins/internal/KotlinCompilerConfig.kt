package pl.mkwiecinski.plugins.internal

import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.jvm.toolchain.JavaLanguageVersion
import org.jetbrains.kotlin.gradle.plugin.getKotlinPluginVersion
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

internal fun Project.configureCompilerFlags() {
    tasks.withType(KotlinCompile::class.java).configureEach {
        it.kotlinOptions {
            freeCompilerArgs = freeCompilerArgs +
                "-Xopt-in=kotlinx.coroutines.ExperimentalCoroutinesApi" +
                "-Xopt-in=kotlin.RequiresOptIn" +
                "-Xjvm-default=enable"
        }
    }
    extensions.getByType(JavaPluginExtension::class.java).toolchain {
        it.languageVersion.set(JavaLanguageVersion.of(17))
    }
}

internal fun Project.setupCommonKotlinVersion() {
    val kotlinVersion = project.getKotlinPluginVersion()

    configurations.configureEach { configuration ->
        configuration.resolutionStrategy.eachDependency {
            if (it.requested.group == "org.jetbrains.kotlin" && it.requested.name.startsWith("kotlin")) {
                it.useVersion(kotlinVersion)
            }
        }
    }
}
