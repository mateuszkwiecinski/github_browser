package pl.mkwiecinski.domain.listing.models

class PagedResult<T>(
    val data: List<T>,
    val nextPageKey: String?,
)
