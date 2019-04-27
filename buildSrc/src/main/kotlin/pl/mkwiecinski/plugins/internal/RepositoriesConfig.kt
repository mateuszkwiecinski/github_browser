package pl.mkwiecinski.plugins.internal

import org.gradle.api.Project

internal fun Project.configureRepositories() {
    repositories.jcenter()
    repositories.google()
    repositories.maven { repository ->
        repository.setUrl("https://oss.sonatype.org/content/repositories/snapshots/")
        repository.mavenContent {
            it.snapshotsOnly()
        }
    }
}
