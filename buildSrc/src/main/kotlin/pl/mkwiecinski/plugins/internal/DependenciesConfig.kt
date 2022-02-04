package pl.mkwiecinski.plugins.internal

import com.android.tools.r8.internal.it
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension

internal fun Project.applyDagger() {
    val versionCatalog = extensions.getByType(VersionCatalogsExtension::class.java).named("libs")
    if (!pluginManager.hasPlugin("org.jetbrains.kotlin.kapt")) {
        pluginManager.apply("org.jetbrains.kotlin.kapt")
    }
    dependencies.add("implementation", versionCatalog.findDependency("dagger-core").get())
    dependencies.add("kapt", versionCatalog.findDependency("dagger-compiler").get())
}

internal fun Project.applyDaggerAndroid() {
    if (!pluginManager.hasPlugin("org.jetbrains.kotlin.kapt")) {
        pluginManager.apply("org.jetbrains.kotlin.kapt")
    }

    dependencies.add("implementation", versionCatalog.findDependency("dagger-android").get())
    dependencies.add("implementation", versionCatalog.findDependency("dagger-android-support").get())
    dependencies.add("kapt", versionCatalog.findDependency("dagger-compiler-android").get())
}

val Project.versionCatalog
    get() = extensions.getByType(VersionCatalogsExtension::class.java).named("libs")

internal fun Project.configureUnitTest() {
    listOf(
        "mockito-core",
        "mockito-kotlin",
        "assertj-core",
        "junit-core",
        "coroutines-testing",
    )
        .map(versionCatalog::findDependency)
        .forEach {
            dependencies.add("testImplementation", it.get())
        }
}
