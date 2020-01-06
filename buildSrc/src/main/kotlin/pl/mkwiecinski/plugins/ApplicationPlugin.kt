package pl.mkwiecinski.plugins

import com.android.build.gradle.TestedExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import pl.mkwiecinski.plugins.internal.applyDagger
import pl.mkwiecinski.plugins.internal.applyDaggerAndroid
import pl.mkwiecinski.plugins.internal.configureUnitTest

class ApplicationPlugin : Plugin<Project> {

    override fun apply(project: Project): Unit = with(project) {
        pluginManager.apply("com.android.application")
        pluginManager.apply("kotlin-android")
        pluginManager.apply("kotlin-kapt")
        pluginManager.apply("plugin-quality")

        repositories.google()
        repositories.jcenter()

        applyDagger()
        applyDaggerAndroid()

        configureUnitTest()

        extensions.getByType(TestedExtension::class.java).apply {

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
}
