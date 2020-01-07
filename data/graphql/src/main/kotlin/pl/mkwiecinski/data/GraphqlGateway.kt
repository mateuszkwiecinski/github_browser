package pl.mkwiecinski.data

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Input
import io.reactivex.Single
import javax.inject.Inject
import pl.mkwiecinski.data.base.rxEnqueue
import pl.mkwiecinski.data.mappings.toIssueInfo
import pl.mkwiecinski.domain.details.entities.RepositoryDetails
import pl.mkwiecinski.domain.details.gateways.DetailsGateway
import pl.mkwiecinski.domain.listing.entities.RepositoryInfo
import pl.mkwiecinski.domain.listing.entities.RepositoryOwner
import pl.mkwiecinski.domain.listing.gateways.ListingGateway
import pl.mkwiecinski.domain.listing.models.PagedResult
import pl.mkwiecinski.graphql.RepositoriesQuery
import pl.mkwiecinski.graphql.RepositoryDetailsQuery

class GraphqlGateway @Inject constructor(
    private val client: ApolloClient
) : ListingGateway, DetailsGateway {

    override fun getFirstPage(owner: RepositoryOwner, limit: Int): Single<PagedResult<RepositoryInfo>> {
        return client.query(
            RepositoriesQuery(
                owner = owner.name,
                count = limit,
                after = Input.absent()
            )
        )
            .rxEnqueue()
            .map { result ->
                val repositories = result.repositoryOwner?.repositories
                val data =
                    repositories?.nodes?.mapNotNull { it?.toIssueInfo() }
                        ?: throw IllegalStateException("No data")
                val nexPageKey = repositories.pageInfo.endCursor
                PagedResult(data, nexPageKey)
            }
    }

    override fun getPageAfter(owner: RepositoryOwner, pageKey: String, limit: Int) =
        client.query(
            RepositoriesQuery(
                owner = owner.name,
                count = limit,
                after = Input.optional(pageKey)
            )
        )
            .rxEnqueue()
            .map { result ->
                val repositories = result.repositoryOwner?.repositories
                val data =
                    repositories?.nodes?.mapNotNull { it?.toIssueInfo() }
                        ?: throw IllegalStateException("No data")
                val nexPageKey = repositories.pageInfo.endCursor
                PagedResult(data, nexPageKey)
            }

    override fun getRepositoryDetails(owner: RepositoryOwner, name: String): Single<RepositoryDetails> =
        client.query(
            RepositoryDetailsQuery(
                owner = owner.name,
                name = name,
                previewCount = DEFAULT_PREVIEW_COUNT
            )
        )
            .rxEnqueue()
            .map { it.repository?.toIssueInfo() }

    companion object {
        private const val DEFAULT_PREVIEW_COUNT = 5
    }
}
