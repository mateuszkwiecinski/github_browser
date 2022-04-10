package pl.mkwiecinski.domain.details

import pl.mkwiecinski.domain.details.gateways.DetailsGateway
import pl.mkwiecinski.domain.listing.entities.RepositoryOwner
import javax.inject.Inject

class HelloHello @Inject constructor(
    private val gateway: DetailsGateway,
    private val owner: RepositoryOwner,
    private val name: String,
) {

    suspend operator fun invoke() {
        gateway.refresh(
            owner = owner,
            name = name,
        )
    }
}
