package pl.mkwiecinski.domain.details

import pl.mkwiecinski.domain.details.gateways.DetailsGateway
import pl.mkwiecinski.domain.listing.entities.RepositoryOwner
import javax.inject.Inject

class GetRepositoryDetailsUseCase @Inject constructor(
    private val gateway: DetailsGateway,
    private val owner: RepositoryOwner,
    private val name: String,
) {

    operator fun invoke() = gateway.getRepositoryDetails(owner, name)
}
