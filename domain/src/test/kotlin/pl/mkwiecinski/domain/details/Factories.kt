package pl.mkwiecinski.domain.details

import pl.mkwiecinski.domain.details.entities.IssuePreview
import pl.mkwiecinski.domain.details.entities.IssuesInfo
import pl.mkwiecinski.domain.details.entities.PullRequestPreview
import pl.mkwiecinski.domain.details.entities.PullRequestsInfo
import pl.mkwiecinski.domain.details.entities.RepositoryDetails
import pl.mkwiecinski.domain.listing.entities.RepositoryInfo

internal fun info(id: String) = RepositoryInfo(
    id = id,
    name = "name: $id",
    url = "url: $id"
)

internal fun details(id: String) = RepositoryDetails(
    id = id,
    name = "name: $id",
    url = "url: $id",
    issues = IssuesInfo(
        openedTotalCount = 100,
        openedPreview = listOf(issuePreview("1")),
        closedTotalCount = 10,
        closedPreview = emptyList()
    ),
    pullRequests = PullRequestsInfo(
        openedTotalCount = 200,
        openedPreview = listOf(prPreview("1")),
        closedTotalCount = 30,
        closedPreview = emptyList()
    )
)

fun issuePreview(id: String) = IssuePreview(
    id = "issue $id",
    name = "name $id",
    number = id.hashCode(),
    url = "url: $id"
)

fun prPreview(id: String) = PullRequestPreview(
    id = "issue $id",
    name = "name $id",
    number = id.hashCode(),
    url = "url: $id"
)
