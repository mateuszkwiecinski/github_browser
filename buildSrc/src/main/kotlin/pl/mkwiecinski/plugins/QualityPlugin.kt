package pl.mkwiecinski.plugins

import com.android.build.gradle.internal.tasks.factory.dependsOn
import io.gitlab.arturbosch.detekt.extensions.DetektExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.jmailen.gradle.kotlinter.KotlinterExtension

class QualityPlugin : Plugin<Project> {

    override fun apply(project: Project) = with(project) {
        tasks.register(QUALITY_TASK_NAME)
        tasks.named("check").dependsOn(QUALITY_TASK_NAME)
        project.afterEvaluate {
            it.addDetekt()
            it.addKtlint()
        }
    }

    private fun Project.addDetekt() {
        pluginManager.apply("io.gitlab.arturbosch.detekt")
        extensions.getByType(DetektExtension::class.java).apply {
            input = files("src")
            reports.apply {
                html { enabled = false }
                xml { enabled = false }
                txt { enabled = false }
            }
        }

        tasks.named(QUALITY_TASK_NAME) {
            it.dependsOn("detekt")
        }
    }

    private fun Project.addKtlint() {
        pluginManager.apply("org.jmailen.kotlinter")
        extensions.configure(KotlinterExtension::class.java) {
            it.experimentalRules = true
            it.reporters = arrayOf("plain")
        }
        tasks.named(QUALITY_TASK_NAME) { qualityTask ->
            qualityTask.dependsOn("lintKotlin")
        }
    }

    companion object {
        private const val QUALITY_TASK_NAME = "projectCodestyle"
    }
}
