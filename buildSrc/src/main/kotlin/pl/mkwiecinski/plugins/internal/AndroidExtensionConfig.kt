package pl.mkwiecinski.plugins.internal

import com.android.build.gradle.TestedExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

private const val COMPILE_SDK_VERSION = 28
private const val MIN_SDK_VERSION = 26

internal fun Project.configureAndroidExtension() {
    extensions.getByType(TestedExtension::class.java).apply {
        compileSdkVersion(COMPILE_SDK_VERSION)
        defaultConfig.apply {
            minSdkVersion(MIN_SDK_VERSION)
            targetSdkVersion(COMPILE_SDK_VERSION)
        }

        useLibrary("android.test.runner")
        useLibrary("android.test.base")
        useLibrary("android.test.mock")

        configureSourceSets()

        compileOptions.sourceCompatibility = JavaVersion.VERSION_1_8
        compileOptions.targetCompatibility = JavaVersion.VERSION_1_8
    }

    tasks.withType(KotlinCompile::class.java).all {
        it.kotlinOptions.jvmTarget = JavaVersion.VERSION_1_8.toString()
    }
}

private fun TestedExtension.configureSourceSets() {
    sourceSets.all { set ->
        val withKotlin = set.java.srcDirs.map { it.path.replace("java", "kotlin") }
        set.java.setSrcDirs(set.java.srcDirs + withKotlin)
    }
}
