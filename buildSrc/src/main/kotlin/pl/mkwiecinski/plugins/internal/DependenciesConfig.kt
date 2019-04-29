package pl.mkwiecinski.plugins.internal

import AndroidTestLibs
import Kapt
import Libs
import TestLibs
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.plugin.KaptExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

internal fun Project.applyKotlinStdLib() {
    dependencies.add("implementation", Libs.kotlinStdlib)

    tasks.withType(KotlinCompile::class.java).all {
        it.kotlinOptions {
            jvmTarget = JavaVersion.VERSION_1_8.toString()
        }
    }
}

internal fun Project.applyDagger() {
    extensions.configure(KaptExtension::class.java) {
        it.correctErrorTypes = true
        it.useBuildCache = true
        it.arguments {
            arg("dagger.formatGeneratedSource", "disabled")
            arg("dagger.gradle.incremental")
        }
    }
    dependencies.add("implementation", Libs.dagger)
    dependencies.add("kapt", Kapt.daggerCompiler)
}

internal fun Project.applyDaggerAndroid() {
    dependencies.add("implementation", Libs.daggerAndroid)
    dependencies.add("implementation", Libs.daggerAndroidSupport)
    dependencies.add("kapt", Kapt.daggerAndroidCompiler)
    dependencies.add("kaptAndroidTest", Kapt.daggerAndroidCompiler)
    dependencies.add("kaptAndroidTest", Kapt.daggerCompiler)
}

internal fun Project.configureUnitTest() {
    dependencies.add("testImplementation", TestLibs.junit4)
    dependencies.add("testImplementation", TestLibs.mockitoKotlin)
    dependencies.add("testImplementation", TestLibs.mockito)
    dependencies.add("testImplementation", TestLibs.assertJ)
}

internal fun Project.configureAndroidTest() {
    dependencies.add("androidTestImplementation", AndroidTestLibs.archCoreKtx)
    dependencies.add("androidTestImplementation", AndroidTestLibs.testExtJunit)
    dependencies.add("androidTestImplementation", AndroidTestLibs.testRunner)
    dependencies.add("androidTestImplementation", AndroidTestLibs.espressoCore)
    dependencies.add("androidTestImplementation", AndroidTestLibs.archNavigation)
}
