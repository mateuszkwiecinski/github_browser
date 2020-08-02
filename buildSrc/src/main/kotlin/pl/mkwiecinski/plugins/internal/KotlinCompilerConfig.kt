package pl.mkwiecinski.plugins.internal

import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

internal fun Project.configureCompilerFlags() {
    tasks.withType(KotlinCompile::class.java).configureEach {
        it.kotlinOptions {
            freeCompilerArgs =
                freeCompilerArgs + "-Xopt-in=kotlinx.coroutines.ExperimentalCoroutinesApi" +
                "-Xopt-in=kotlinx.coroutines.FlowPreview" +
                "-Xopt-in=kotlin.RequiresOptIn" +
                "-Xopt-in=kotlin.time.ExperimentalTime"
        }
    }
}
