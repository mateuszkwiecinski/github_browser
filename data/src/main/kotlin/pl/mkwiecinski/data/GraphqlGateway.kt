package pl.mkwiecinski.data

import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import io.reactivex.Single
import pl.mkwiecinski.data.mappings.toDomain
import pl.mkwiecinski.domain.entities.RepositoryInfo
import pl.mkwiecinski.domain.gateways.RepoGateway
import pl.mkwiecinski.domain.paging.models.PagedResult
import pl.mkwiecinski.graphql.RepositoriesQuery
import javax.inject.Inject

class GraphqlGateway @Inject constructor(
    private val client: ApolloClient
) : RepoGateway {

    override fun getFirstPage(owner: String, limit: Int): Single<PagedResult<RepositoryInfo>> {
        return client.query(RepositoriesQuery.builder().apply {
            owner(owner)
            count(limit)
        }.build())
            .rxEnqueue()
            .map { result ->
                val repositories = result.repositoryOwner()?.repositories()
                val data = repositories?.nodes()?.map { it.toDomain() }
                    ?: throw IllegalStateException("No data")
                val nexPageKey = repositories.pageInfo().endCursor()
                PagedResult(data, nexPageKey)
            }
    }

    override fun getPageAfter(owner: String, pageKey: String, limit: Int): Single<PagedResult<RepositoryInfo>> =
        client.query(RepositoriesQuery.builder().apply {
            owner(owner)
            count(limit)
            after(pageKey)
        }.build())
            .rxEnqueue()
            .map { result ->
                val repositories = result.repositoryOwner()?.repositories()
                val data = repositories?.nodes()?.map { it.toDomain() }
                    ?: throw IllegalStateException("No data")
                val nexPageKey = repositories.pageInfo().endCursor()
                PagedResult(data, nexPageKey)
            }

    private fun <T> ApolloCall<T>.rxEnqueue(): Single<T> {
        return Single.create<T> { emitter ->
            enqueue(object : ApolloCall.Callback<T>() {
                override fun onFailure(exception: ApolloException) {
                    emitter.onError(exception)
                }

                override fun onResponse(response: Response<T>) {
                    emitter.onSuccess(response.data()!!)
                }
            })
            emitter.setCancellable { cancel() }
        }
    }
}
