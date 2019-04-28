package pl.mkwiecinski.domain.listing.paging

import androidx.paging.PageKeyedDataSource
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import pl.mkwiecinski.domain.base.plusAssign
import pl.mkwiecinski.domain.listing.entities.RepositoryInfo
import pl.mkwiecinski.domain.listing.entities.RepositoryOwner
import pl.mkwiecinski.domain.listing.gateways.ListingGateway
import javax.inject.Inject

internal class RepoDataSource @Inject constructor(
    private val gateway: ListingGateway,
    private val events: InMemoryPagingEvents,
    private val repositoryOwner: RepositoryOwner
) : PageKeyedDataSource<String, RepositoryInfo>(), Disposable {

    private val disposeBag = CompositeDisposable()

    internal var retry: () -> Unit = { }
        private set

    override fun loadInitial(params: LoadInitialParams<String>, callback: LoadInitialCallback<String, RepositoryInfo>) {
        disposeBag += gateway.getFirstPage(repositoryOwner, params.requestedLoadSize)
            .doOnSubscribe { events.onNetworkCall() }
            .subscribe(
                {
                    retry = {}
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
        disposeBag += call.subscribe(
            {
                retry = {}
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

    override fun dispose() = disposeBag.dispose()

    override fun isDisposed() = disposeBag.isDisposed
}
