package pl.mkwiecinski.domain.details

import javax.inject.Inject
import pl.mkwiecinski.domain.details.entities.RepositoryDetails
import pl.mkwiecinski.domain.details.gateways.DetailsGateway
import pl.mkwiecinski.domain.listing.entities.RepositoryOwner

class GetRepositoryDetailsUseCase @Inject constructor(
    private val gateway: DetailsGateway,
    private val owner: RepositoryOwner
) {

    suspend operator fun invoke(name: String): RepositoryDetails =
        gateway.getRepositoryDetails(owner, name)
}
