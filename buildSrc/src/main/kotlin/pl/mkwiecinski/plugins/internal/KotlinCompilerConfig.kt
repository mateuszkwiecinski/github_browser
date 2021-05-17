package pl.mkwiecinski.plugins.internal

import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.plugin.KotlinBasePluginWrapper
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

internal fun Project.configureCompilerFlags() {
    tasks.withType(KotlinCompile::class.java).configureEach {
        it.kotlinOptions {
            freeCompilerArgs = freeCompilerArgs +
                "-Xopt-in=kotlinx.coroutines.ExperimentalCoroutinesApi" +
                "-Xopt-in=kotlin.RequiresOptIn" +
                "-Xopt-in=kotlin.time.ExperimentalTime"
        }
    }
}

internal fun Project.setupCommonKotlinVersion() {
    val kotlinVersion = plugins.asSequence().filterIsInstance<KotlinBasePluginWrapper>().first().kotlinPluginVersion

    configurations.configureEach { configuration ->
        configuration.resolutionStrategy.eachDependency {
            if (it.requested.group == "org.jetbrains.kotlin" && it.requested.name.startsWith("kotlin")) {
                it.useVersion(kotlinVersion)
            }
        }
    }
}
