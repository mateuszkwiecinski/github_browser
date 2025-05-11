package pl.mkwiecinski.data

import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Operation
import com.apollographql.apollo.api.Optional
import com.apollographql.apollo.cache.normalized.FetchPolicy
import com.apollographql.apollo.cache.normalized.NetworkOnlyInterceptor
import com.apollographql.apollo.cache.normalized.fetchPolicy
import com.apollographql.apollo.cache.normalized.fetchPolicyInterceptor
import com.apollographql.apollo.cache.normalized.refetchPolicy
import com.apollographql.apollo.cache.normalized.watch
import com.apollographql.apollo.exception.DefaultApolloException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import pl.mkwiecinski.data.mappings.toIssueInfo
import pl.mkwiecinski.domain.details.gateways.DetailsGateway
import pl.mkwiecinski.domain.listing.entities.RepositoryInfo
import pl.mkwiecinski.domain.listing.entities.RepositoryOwner
import pl.mkwiecinski.domain.listing.gateways.ListingGateway
import pl.mkwiecinski.domain.listing.models.PagedResult
import pl.mkwiecinski.graphql.RepositoriesQuery
import pl.mkwiecinski.graphql.RepositoryDetailsQuery
import javax.inject.Inject

internal class GraphqlGateway @Inject constructor(private val client: ApolloClient, private val dispatcher: CoroutineDispatcher) :
    ListingGateway,
    DetailsGateway {

    override suspend fun getFirstPage(owner: RepositoryOwner, limit: Int): PagedResult<RepositoryInfo> = withContext(dispatcher) {
        val result = client.query(
            RepositoriesQuery(
                owner = owner.name,
                count = limit,
                after = Optional.Absent,
            ),
        ).fetchPolicyInterceptor(NetworkOnlyInterceptor).getDataOrThrow()
        val repositories = result.repositoryOwner?.repositories
        val data = repositories?.nodes?.mapNotNull { it?.toIssueInfo() }
        checkNotNull(data) { "No data" }

        val nexPageKey = repositories.pageInfo.endCursor
        PagedResult(data, nexPageKey)
    }

    override suspend fun getPageAfter(owner: RepositoryOwner, pageKey: String, limit: Int): PagedResult<RepositoryInfo> =
        withContext(dispatcher) {
            val result = client.query(
                RepositoriesQuery(
                    owner = owner.name,
                    count = limit,
                    after = Optional.presentIfNotNull(pageKey),
                ),
            ).fetchPolicyInterceptor(NetworkOnlyInterceptor).getDataOrThrow()
            val repositories = result.repositoryOwner?.repositories
            val data = repositories?.nodes?.mapNotNull { it?.toIssueInfo() }
            checkNotNull(data) { "No data" }

            val nexPageKey = repositories.pageInfo.endCursor
            PagedResult(data, nexPageKey)
        }

    override fun getRepositoryDetails(owner: RepositoryOwner, name: String) = client.query(repositoryDetailsQuery(owner, name))
        .fetchPolicy(FetchPolicy.CacheOnly)
        .refetchPolicy(FetchPolicy.CacheOnly)
        .watch()
        .map { it.data?.repository?.toIssueInfo() }

    override suspend fun refresh(owner: RepositoryOwner, name: String) {
        client.query(repositoryDetailsQuery(owner, name))
            .fetchPolicy(FetchPolicy.NetworkOnly)
            .getDataOrThrow()
    }

    private fun repositoryDetailsQuery(owner: RepositoryOwner, name: String) = RepositoryDetailsQuery(
        owner = owner.name,
        name = name,
        previewCount = DEFAULT_PREVIEW_COUNT,
    )

    private suspend fun <T : Operation.Data> ApolloCall<T>.getDataOrThrow() = execute().let {
        it.data ?: throw DefaultApolloException(it.errors.orEmpty().joinToString(separator = ","))
    }

    companion object {
        private const val DEFAULT_PREVIEW_COUNT = 5
    }
}
