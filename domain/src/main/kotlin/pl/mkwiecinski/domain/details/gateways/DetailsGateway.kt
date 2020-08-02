package pl.mkwiecinski.domain.details.gateways

import pl.mkwiecinski.domain.details.entities.RepositoryDetails
import pl.mkwiecinski.domain.listing.entities.RepositoryOwner

interface DetailsGateway {

    suspend fun getRepositoryDetails(owner: RepositoryOwner, name: String): RepositoryDetails
}
