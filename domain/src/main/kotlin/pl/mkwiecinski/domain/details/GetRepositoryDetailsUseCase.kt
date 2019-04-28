package pl.mkwiecinski.domain.details

import io.reactivex.Single
import pl.mkwiecinski.domain.details.entities.RepositoryDetails
import pl.mkwiecinski.domain.details.gateways.DetailsGateway
import pl.mkwiecinski.domain.listing.entities.RepositoryOwner
import javax.inject.Inject

class GetRepositoryDetailsUseCase @Inject constructor(
    private val gateway: DetailsGateway,
    private val owner: RepositoryOwner
) {

    operator fun invoke(name: String): Single<RepositoryDetails> =
        gateway.getRepositoryDetails(owner, name)
}
