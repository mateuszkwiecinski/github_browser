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
                "-opt-in=kotlinx.coroutines.ExperimentalCoroutinesApi" +
                "-opt-in=kotlin.RequiresOptIn" +
                "-Xjvm-default=all"
        }
    }
    extensions.getByType(JavaPluginExtension::class.java).toolchain {
        it.languageVersion.set(JavaLanguageVersion.of(17))
    }
}

internal fun Project.setupCommonKotlinVersion() {
    val kotlinVersion = project.getKotlinPluginVersion()

    configurations.configureEach { configuration ->
        configuration.resolutionStrategy.matching { it.name != "detekt" }.eachDependency {
            if (it.requested.group == "org.jetbrains.kotlin" && it.requested.name.startsWith("kotlin")) {
                it.useVersion(kotlinVersion)
            }
        }
    }
}
