package pl.mkwiecinski.domain.listing.gateways

import io.reactivex.Single
import pl.mkwiecinski.domain.listing.entities.RepositoryInfo
import pl.mkwiecinski.domain.listing.entities.RepositoryOwner
import pl.mkwiecinski.domain.listing.models.PagedResult

interface ListingGateway {

    fun getFirstPage(owner: RepositoryOwner, limit: Int): Single<PagedResult<RepositoryInfo>>

    fun getPageAfter(owner: RepositoryOwner, pageKey: String, limit: Int): Single<PagedResult<RepositoryInfo>>
}
