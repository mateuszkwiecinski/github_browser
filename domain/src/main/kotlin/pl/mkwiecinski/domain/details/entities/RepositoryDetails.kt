package pl.mkwiecinski.domain.details.entities

data class RepositoryDetails(
    val id: String,
    val name: String,
    val url: String,
    val openedIssues: IssueData,
    val closedIssues: IssueData,
    val openedPullRequests: PullRequestData,
    val closedPullRequests: PullRequestData
)
