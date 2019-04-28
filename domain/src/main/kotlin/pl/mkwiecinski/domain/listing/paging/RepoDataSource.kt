package pl.mkwiecinski.domain.listing.paging

import androidx.paging.PageKeyedDataSource
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import pl.mkwiecinski.domain.base.plusAssign
import pl.mkwiecinski.domain.listing.entities.RepositoryInfo
import pl.mkwiecinski.domain.listing.gateways.ListingGateway
import pl.mkwiecinski.domain.listing.entities.RepositoryOwner

internal class RepoDataSource(
    private val gateway: ListingGateway,
    private val events: InMemoryPagingEvents,
    private val compositeDisposable: CompositeDisposable,
    private val repositoryOwner: RepositoryOwner
) : PageKeyedDataSource<String, RepositoryInfo>(), Disposable by compositeDisposable {

    internal var retry: () -> Unit = { }
        private set

    override fun loadInitial(params: LoadInitialParams<String>, callback: LoadInitialCallback<String, RepositoryInfo>) {
        compositeDisposable += gateway.getFirstPage(repositoryOwner, params.requestedLoadSize)
            .doOnSubscribe { events.onNetworkCall() }
            .subscribe(
                {
                    events.onLoadSuccessful()
                    callback.onResult(it.data, null, it.nextPageKey)
                },
                {
                    retry = { loadInitial(params, callback) }
                    events.onLoadError()
                }
            )
    }

    override fun loadAfter(params: LoadParams<String>, callback: LoadCallback<String, RepositoryInfo>) {
        val call =
            gateway.getPageAfter(repositoryOwner, params.key, params.requestedLoadSize)
                .doOnSubscribe { events.onNetworkCall() }
        compositeDisposable += call.subscribe(
            {
                events.onLoadSuccessful()
                callback.onResult(it.data, it.nextPageKey)
            },
            {
                retry = { loadAfter(params, callback) }
                events.onLoadError()
            }
        )
    }

    override fun loadBefore(params: LoadParams<String>, callback: LoadCallback<String, RepositoryInfo>) = Unit
}
