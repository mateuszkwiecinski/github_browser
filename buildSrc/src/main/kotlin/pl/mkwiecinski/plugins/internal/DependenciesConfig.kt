package pl.mkwiecinski.plugins.internal

import Kapt
import Libs
import TestLibs
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.plugin.KaptExtension

internal fun Project.applyDagger() {
    extensions.configure(KaptExtension::class.java) {
        it.correctErrorTypes = true
        it.useBuildCache = true
        it.arguments {
            arg("dagger.formatGeneratedSource", "disabled")
        }
    }
    dependencies.add("implementation", Libs.dagger)
    dependencies.add("kapt", Kapt.daggerCompiler)
}

internal fun Project.applyDaggerAndroid() {
    dependencies.add("implementation", Libs.daggerAndroid)
    dependencies.add("implementation", Libs.daggerAndroidSupport)
    dependencies.add("kapt", Kapt.daggerAndroidCompiler)
}

internal fun Project.configureUnitTest() {
    dependencies.add("testImplementation", TestLibs.junit4)
    dependencies.add("testImplementation", TestLibs.mockitoKotlin)
    dependencies.add("testImplementation", TestLibs.mockito)
    dependencies.add("testImplementation", TestLibs.assertJ)
}
