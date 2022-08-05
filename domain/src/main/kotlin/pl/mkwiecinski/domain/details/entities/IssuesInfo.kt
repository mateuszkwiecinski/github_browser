package pl.mkwiecinski.domain.details.entities

data class IssuesInfo(
    val openedTotalCount: Int,
    val openedPreview: List<IssuePreview>,
    val closedTotalCount: Int,
    val closedPreview: List<IssuePreview>,
)

data class IssuePreview(
    val id: String,
    val number: Int,
    val name: String,
    val url: String,
)
