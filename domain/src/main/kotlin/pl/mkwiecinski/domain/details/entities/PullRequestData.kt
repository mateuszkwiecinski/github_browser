package pl.mkwiecinski.domain.details.entities

data class PullRequestData(
    val preview: List<PullRequestInfo>,
    val totalCount: Int
)

data class PullRequestInfo(
    val id: String,
    val number: Int,
    val name: String,
    val url: String
)
