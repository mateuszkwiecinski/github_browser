package pl.mkwiecinski.presentation.details.vm

import pl.mkwiecinski.domain.details.entities.IssuesInfo
import pl.mkwiecinski.domain.details.entities.PullRequestsInfo
import pl.mkwiecinski.domain.details.entities.RepositoryDetails


internal fun details(id: String) = RepositoryDetails(
    id = id,
    name = "name$id",
    url = "url$id",
    issues = IssuesInfo(10, emptyList(), 0, emptyList()),
    pullRequests = PullRequestsInfo(20, emptyList(), 0, emptyList())
)
