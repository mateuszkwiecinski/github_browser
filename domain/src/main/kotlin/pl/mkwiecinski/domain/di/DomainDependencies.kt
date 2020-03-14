package pl.mkwiecinski.domain.di

import pl.mkwiecinski.domain.details.gateways.DetailsGateway
import pl.mkwiecinski.domain.listing.gateways.ListingGateway
import pl.mkwiecinski.domain.listing.paging.PagedListBuilder

interface DomainDependencies {

    fun listing(): ListingGateway

    fun details(): DetailsGateway

    fun pagedListBuilder(): PagedListBuilder
}
