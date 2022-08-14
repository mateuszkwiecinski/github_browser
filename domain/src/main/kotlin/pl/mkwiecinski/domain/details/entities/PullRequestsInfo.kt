package pl.mkwiecinski.domain.details.entities

/**
 * Despite it looks very similar to [IssuesInfo], they both represent different business state, so they should be kept separately
 */
data class PullRequestsInfo(
    val openedTotalCount: Int,
    val openedPreview: List<PullRequestPreview>,
    val closedTotalCount: Int,
    val closedPreview: List<PullRequestPreview>,
)

data class PullRequestPreview(
    val id: String,
    val number: Int,
    val name: String,
    val url: String,
)
