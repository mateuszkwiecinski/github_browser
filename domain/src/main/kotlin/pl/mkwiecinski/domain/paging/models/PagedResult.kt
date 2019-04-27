package pl.mkwiecinski.domain.paging.models

class PagedResult<T>(
    val data: List<T>,
    val nextPageKey: String?
)
