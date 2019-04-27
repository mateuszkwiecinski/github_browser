package pl.mkwiecinski.data.mappings

import pl.mkwiecinski.domain.entities.RepositoryInfo
import pl.mkwiecinski.graphql.RepositoriesQuery

internal fun RepositoriesQuery.Node.toDomain() =
    RepositoryInfo(
        name(),
        url().toString()
    )
