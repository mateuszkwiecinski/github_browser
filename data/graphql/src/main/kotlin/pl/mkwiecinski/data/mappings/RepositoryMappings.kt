package pl.mkwiecinski.data.mappings

import pl.mkwiecinski.domain.details.entities.IssuePreview
import pl.mkwiecinski.domain.details.entities.IssuesInfo
import pl.mkwiecinski.domain.details.entities.PullRequestPreview
import pl.mkwiecinski.domain.details.entities.PullRequestsInfo
import pl.mkwiecinski.domain.details.entities.RepositoryDetails
import pl.mkwiecinski.domain.listing.entities.RepositoryInfo
import pl.mkwiecinski.graphql.RepositoriesQuery
import pl.mkwiecinski.graphql.RepositoryDetailsQuery

internal fun RepositoriesQuery.Node.toIssueInfo() =
    RepositoryInfo(
        id,
        name,
        url.toString(),
    )

internal fun RepositoryDetailsQuery.Repository.toIssueInfo() =
    RepositoryDetails(
        id = id,
        name = name,
        url = url.toString(),
        issues = toIssuesInfo(),
        pullRequests = toPullRequestData(),
    )

private fun RepositoryDetailsQuery.Repository.toIssuesInfo() = IssuesInfo(
    openedTotalCount = openedIssues.totalCount,
    openedPreview = openedIssues.nodes.orEmpty().mapNotNull { it?.toIssueInfo() },
    closedTotalCount = closedIssues.totalCount,
    closedPreview = closedIssues.nodes.orEmpty().mapNotNull { it?.toIssueInfo() },
)

private fun RepositoryDetailsQuery.Repository.toPullRequestData() = PullRequestsInfo(
    openedTotalCount = openedPRs.totalCount,
    openedPreview = openedPRs.nodes.orEmpty().mapNotNull { it?.toPullRequestInfo() },
    closedTotalCount = closedPRs.totalCount,
    closedPreview = closedPRs.nodes.orEmpty().mapNotNull { it?.toPullRequestInfo() },
)

private fun RepositoryDetailsQuery.Node1.toIssueInfo() = IssuePreview(
    id = id,
    name = title,
    url = url.toString(),
    number = number,
)

private fun RepositoryDetailsQuery.Node.toIssueInfo() = IssuePreview(
    id = id,
    name = title,
    url = url.toString(),
    number = number,
)

private fun RepositoryDetailsQuery.Node2.toPullRequestInfo() = PullRequestPreview(
    id = id,
    name = title,
    url = url.toString(),
    number = number,
)

private fun RepositoryDetailsQuery.Node3.toPullRequestInfo() = PullRequestPreview(
    id = id,
    name = title,
    url = url.toString(),
    number = number,
)
