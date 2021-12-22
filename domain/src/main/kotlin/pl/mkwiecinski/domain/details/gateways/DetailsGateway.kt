package pl.mkwiecinski.domain.details.gateways

import kotlinx.coroutines.flow.Flow
import pl.mkwiecinski.domain.details.entities.RepositoryDetails
import pl.mkwiecinski.domain.listing.entities.RepositoryOwner

interface DetailsGateway {

    fun getRepositoryDetails(owner: RepositoryOwner, name: String): Flow<RepositoryDetails?>

    suspend fun refresh(owner: RepositoryOwner, name: String)
}
