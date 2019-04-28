package pl.mkwiecinski.domain.details.entities

data class RepositoryDetails(
    val id: String,
    val name: String,
    val url: String,
    val issues: IssuesInfo,
    val pullRequests: PullRequestsInfo
)
