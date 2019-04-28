package pl.mkwiecinski.data.mappings

import pl.mkwiecinski.domain.details.entities.IssueData
import pl.mkwiecinski.domain.details.entities.IssueInfo
import pl.mkwiecinski.domain.details.entities.PullRequestData
import pl.mkwiecinski.domain.details.entities.PullRequestInfo
import pl.mkwiecinski.domain.details.entities.RepositoryDetails
import pl.mkwiecinski.domain.listing.entities.RepositoryInfo
import pl.mkwiecinski.graphql.RepositoriesQuery
import pl.mkwiecinski.graphql.RepositoryDetailsQuery

internal fun RepositoriesQuery.Node.toIssueInfo() =
    RepositoryInfo(
        id(),
        name(),
        url().toString()
    )

internal fun RepositoryDetailsQuery.Repository.toIssueInfo() =
    RepositoryDetails(
        id = id(),
        name = name(),
        url = url().toString(),
        openedIssues = openedIssues().toIssueData(),
        closedIssues = closedIssues().toIssueData(),
        openedPullRequests = openedPRs().toPullRequestData(),
        closedPullRequests = closedPRs().toPullRequestData()
    )

private fun RepositoryDetailsQuery.OpenedIssues.toIssueData() = IssueData(
    totalCount = totalCount(),
    preview = nodes().orEmpty().map { it.toIssueInfo() }
)

private fun RepositoryDetailsQuery.ClosedIssues.toIssueData() = IssueData(
    totalCount = totalCount(),
    preview = nodes().orEmpty().map { it.toIssueInfo() }
)

private fun RepositoryDetailsQuery.Node1.toIssueInfo() = IssueInfo(
    id = id(),
    name = title(),
    url = url().toString(),
    number = number()
)

private fun RepositoryDetailsQuery.Node.toIssueInfo() = IssueInfo(
    id = id(),
    name = title(),
    url = url().toString(),
    number = number()
)
private fun RepositoryDetailsQuery.ClosedPRs.toPullRequestData() = PullRequestData(
    totalCount = totalCount(),
    preview = nodes().orEmpty().map { it.toPullRequestInfo() }
)

private fun RepositoryDetailsQuery.OpenedPRs.toPullRequestData() = PullRequestData(
    totalCount = totalCount(),
    preview = nodes().orEmpty().map { it.toPullRequestInfo() }
)

private fun RepositoryDetailsQuery.Node2.toPullRequestInfo() = PullRequestInfo(
    id = id(),
    name = title(),
    url = url().toString(),
    number = number()
)

private fun RepositoryDetailsQuery.Node3.toPullRequestInfo() = PullRequestInfo(
    id = id(),
    name = title(),
    url = url().toString(),
    number = number()
)
