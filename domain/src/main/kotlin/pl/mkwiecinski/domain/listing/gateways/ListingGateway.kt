package pl.mkwiecinski.domain.listing.gateways

import pl.mkwiecinski.domain.listing.entities.RepositoryInfo
import pl.mkwiecinski.domain.listing.entities.RepositoryOwner
import pl.mkwiecinski.domain.listing.models.PagedResult

interface ListingGateway {

    suspend fun getFirstPage(owner: RepositoryOwner, limit: Int): PagedResult<RepositoryInfo>

    suspend fun getPageAfter(
        owner: RepositoryOwner,
        pageKey: String,
        limit: Int
    ): PagedResult<RepositoryInfo>
}
