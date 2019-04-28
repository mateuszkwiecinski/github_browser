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
        addDetekt()
        addKtlint()
    }

    private fun Project.addDetekt() {
        pluginManager.apply("io.gitlab.arturbosch.detekt")
        extensions.getByType(DetektExtension::class.java).apply {
            input = files("src")
            filters = ".*/resources/.*,.*/build/.*"
            reports.html { enabled = false }
            reports.xml { enabled = false }
        }

        tasks.named(QUALITY_TASK_NAME) {
            it.dependsOn("detekt")
        }
    }

    private fun Project.addKtlint() {
        pluginManager.apply("org.jmailen.kotlinter")
        extensions.configure(KotlinterExtension::class.java) {
            it.indentSize = INDENTATION_SIZE
            it.continuationIndentSize = INDENTATION_SIZE
            it.reporters = arrayOf("plain")
        }
        tasks.named(QUALITY_TASK_NAME) { qualityTask ->
            qualityTask.dependsOn("lintKotlin")
        }
    }

    companion object {
        private const val INDENTATION_SIZE = 4
        private const val QUALITY_TASK_NAME = "projectCodestyle"
    }
}
