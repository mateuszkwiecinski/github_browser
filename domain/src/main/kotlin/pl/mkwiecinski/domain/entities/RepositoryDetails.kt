package pl.mkwiecinski.domain.entities

data class RepositoryDetails(
    val id: String,
    val name: String,
    val url: String,
    val openedIssues: Int,
    val closedIssues: Int,
    val openedPullRequests: List<PullRequestInfo>,
    val closedPullRequests: List<PullRequestInfo>
)
