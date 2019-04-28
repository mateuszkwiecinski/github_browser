package pl.mkwiecinski.domain.details.entities

data class IssueData(
    val preview: List<IssueInfo>,
    val totalCount: Int
)

data class IssueInfo(
    val id: String,
    val number: Int,
    val name: String,
    val url: String
)
