package pl.mkwiecinski.domain.details.gateways

import io.reactivex.Single
import pl.mkwiecinski.domain.details.entities.RepositoryDetails
import pl.mkwiecinski.domain.listing.entities.RepositoryOwner

interface DetailsGateway {

    fun getRepositoryDetails(owner: RepositoryOwner, name: String): Single<RepositoryDetails>
}
