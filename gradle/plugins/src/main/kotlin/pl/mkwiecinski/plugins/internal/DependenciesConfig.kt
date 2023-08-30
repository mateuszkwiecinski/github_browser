package pl.mkwiecinski.plugins.internal

import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension

internal fun Project.applyDagger() {
    val versionCatalog = extensions.getByType(VersionCatalogsExtension::class.java).named("libs")
    if (!pluginManager.hasPlugin("com.google.devtools.ksp")) {
        pluginManager.apply(versionCatalog.findPlugin("google-ksp").get().get().pluginId)
    }
    dependencies.add("implementation", versionCatalog.findLibrary("dagger-core").get())
    dependencies.add("ksp", versionCatalog.findLibrary("dagger-compiler").get())
}

internal fun Project.applyDaggerAndroid() {
    if (!pluginManager.hasPlugin("org.jetbrains.kotlin.kapt")) {
        pluginManager.apply("org.jetbrains.kotlin.kapt")
    }
    dependencies.add("implementation", versionCatalog.findLibrary("dagger-core").get())
    dependencies.add("implementation", versionCatalog.findLibrary("dagger-android").get())
    dependencies.add("implementation", versionCatalog.findLibrary("dagger-android-support").get())
    dependencies.add("kapt", versionCatalog.findLibrary("dagger-compiler").get())
    dependencies.add("kapt", versionCatalog.findLibrary("dagger-compiler-android").get())
}

private val Project.versionCatalog
    get() = extensions.getByType(VersionCatalogsExtension::class.java).named("libs")

internal fun Project.configureUnitTest() {
    listOf(
        "assertj-core",
        "junit-core",
        "coroutines-testing",
    )
        .map(versionCatalog::findLibrary)
        .forEach {
            dependencies.add("testImplementation", it.get())
        }
}
