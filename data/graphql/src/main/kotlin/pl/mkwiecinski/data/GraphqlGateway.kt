package pl.mkwiecinski.data

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.ApolloQueryCall
import com.apollographql.apollo.api.Input
import com.apollographql.apollo.coroutines.await
import com.apollographql.apollo.exception.ApolloException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import pl.mkwiecinski.data.mappings.toIssueInfo
import pl.mkwiecinski.domain.details.entities.RepositoryDetails
import pl.mkwiecinski.domain.details.gateways.DetailsGateway
import pl.mkwiecinski.domain.listing.entities.RepositoryInfo
import pl.mkwiecinski.domain.listing.entities.RepositoryOwner
import pl.mkwiecinski.domain.listing.gateways.ListingGateway
import pl.mkwiecinski.domain.listing.models.PagedResult
import pl.mkwiecinski.graphql.RepositoriesQuery
import pl.mkwiecinski.graphql.RepositoryDetailsQuery
import javax.inject.Inject

internal class GraphqlGateway @Inject constructor(
    private val client: ApolloClient,
    private val dispatcher: CoroutineDispatcher
) : ListingGateway, DetailsGateway {

    override suspend fun getFirstPage(
        owner: RepositoryOwner,
        limit: Int
    ): PagedResult<RepositoryInfo> = withContext(dispatcher) {
        val result = client.query(
            RepositoriesQuery(
                owner = owner.name,
                count = limit,
                after = Input.absent()
            )
        ).getDataOrThrow()
        val repositories = result.repositoryOwner?.repositories
        val data =
            repositories?.nodes?.mapNotNull { it?.toIssueInfo() }
                ?: throw IllegalStateException("No data")
        val nexPageKey = repositories.pageInfo.endCursor
        PagedResult(data, nexPageKey)
    }

    override suspend fun getPageAfter(
        owner: RepositoryOwner,
        pageKey: String,
        limit: Int
    ): PagedResult<RepositoryInfo> = withContext(dispatcher) {
        val result = client.query(
            RepositoriesQuery(
                owner = owner.name,
                count = limit,
                after = Input.optional(pageKey)
            )
        ).getDataOrThrow()
        val repositories = result.repositoryOwner?.repositories
        val data =
            repositories?.nodes?.mapNotNull { it?.toIssueInfo() }
                ?: throw IllegalStateException("No data")
        val nexPageKey = repositories.pageInfo.endCursor
        PagedResult(data, nexPageKey)
    }

    override suspend fun getRepositoryDetails(
        owner: RepositoryOwner,
        name: String
    ): RepositoryDetails = withContext(dispatcher) {
        val result = client.query(
            RepositoryDetailsQuery(
                owner = owner.name,
                name = name,
                previewCount = DEFAULT_PREVIEW_COUNT
            )
        ).getDataOrThrow()

        result.repository.let(::requireNotNull).toIssueInfo()
    }

    private suspend fun <T> ApolloQueryCall<T>.getDataOrThrow() =
        await().let {
            it.data ?: throw ApolloException(it.errors.orEmpty().joinToString(separator = ","))
        }

    companion object {
        private const val DEFAULT_PREVIEW_COUNT = 5
    }
}
