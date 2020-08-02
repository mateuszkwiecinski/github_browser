package pl.mkwiecinski.domain.listing.paging

import androidx.paging.PageKeyedDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.launch
import pl.mkwiecinski.domain.listing.entities.RepositoryInfo
import pl.mkwiecinski.domain.listing.entities.RepositoryOwner
import pl.mkwiecinski.domain.listing.gateways.ListingGateway
import pl.mkwiecinski.domain.listing.persistences.InMemoryPagingEventsPersistence
import java.io.Closeable
import javax.inject.Inject

internal class RepoDataSource @Inject constructor(
    private val gateway: ListingGateway,
    private val events: InMemoryPagingEventsPersistence,
    private val repositoryOwner: RepositoryOwner,
    private val dispatcher: CoroutineDispatcher
) : PageKeyedDataSource<String, RepositoryInfo>(), Closeable {

    private val job = SupervisorJob()
    private val scope = CoroutineScope(dispatcher + job)
    internal var retry: () -> Unit = { }
        private set

    init {
        addInvalidatedCallback { job.cancelChildren() }
    }

    override fun loadInitial(
        params: LoadInitialParams<String>,
        callback: LoadInitialCallback<String, RepositoryInfo>
    ) {
        scope.launch {
            events.onNetworkCall()
            runCatching { gateway.getFirstPage(repositoryOwner, params.requestedLoadSize) }
                .onSuccess {
                    retry = {}
                    events.onLoadSuccessful()
                    callback.onResult(it.data, null, it.nextPageKey)
                }
                .onFailure {
                    retry = { loadInitial(params, callback) }
                    events.onLoadError()
                }
        }
    }

    override fun loadAfter(
        params: LoadParams<String>,
        callback: LoadCallback<String, RepositoryInfo>
    ) {
        scope.launch {
            events.onNetworkCall()
            runCatching {
                gateway.getPageAfter(
                    repositoryOwner,
                    params.key,
                    params.requestedLoadSize
                )
            }
                .onSuccess {
                    retry = {}
                    events.onLoadSuccessful()
                    callback.onResult(it.data, it.nextPageKey)
                }
                .onFailure {
                    retry = { loadAfter(params, callback) }
                    events.onLoadError()
                }
        }
    }

    override fun loadBefore(
        params: LoadParams<String>,
        callback: LoadCallback<String, RepositoryInfo>
    ) =
        throw UnsupportedOperationException("DataSource does not support loadBefore")

    override fun close() {
        scope.cancel()
    }
}
