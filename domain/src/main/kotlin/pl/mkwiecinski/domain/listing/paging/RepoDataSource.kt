package pl.mkwiecinski.domain.listing.paging

import androidx.paging.PageKeyedDataSource
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import pl.mkwiecinski.domain.base.plusAssign
import pl.mkwiecinski.domain.listing.entities.RepositoryInfo
import pl.mkwiecinski.domain.listing.entities.RepositoryOwner
import pl.mkwiecinski.domain.listing.gateways.ListingGateway
import pl.mkwiecinski.domain.listing.persistences.InMemoryPagingEventsPersistence
import javax.inject.Inject

internal class RepoDataSource @Inject constructor(
    private val gateway: ListingGateway,
    private val events: InMemoryPagingEventsPersistence,
    private val repositoryOwner: RepositoryOwner
) : PageKeyedDataSource<String, RepositoryInfo>(), Disposable {

    private val disposeBag = CompositeDisposable()

    internal var retry: () -> Unit = { }
        private set

    init {
        addInvalidatedCallback(disposeBag::dispose)
    }

    override fun loadInitial(params: LoadInitialParams<String>, callback: LoadInitialCallback<String, RepositoryInfo>) {
        disposeBag += gateway.getFirstPage(repositoryOwner, params.requestedLoadSize)
            .doOnSubscribe { events.onNetworkCall() }
            .subscribeBy(
                onSuccess = {
                    retry = {}
                    events.onLoadSuccessful()
                    callback.onResult(it.data, null, it.nextPageKey)
                },
                onError = {
                    retry = { loadInitial(params, callback) }
                    events.onLoadError()
                }
            )
    }

    override fun loadAfter(params: LoadParams<String>, callback: LoadCallback<String, RepositoryInfo>) {
        disposeBag += gateway.getPageAfter(repositoryOwner, params.key, params.requestedLoadSize)
            .doOnSubscribe { events.onNetworkCall() }
            .subscribeBy(
                onSuccess = {
                    retry = {}
                    events.onLoadSuccessful()
                    callback.onResult(it.data, it.nextPageKey)
                },
                onError = {
                    retry = { loadAfter(params, callback) }
                    events.onLoadError()
                }
            )
    }

    override fun loadBefore(params: LoadParams<String>, callback: LoadCallback<String, RepositoryInfo>) =
        throw UnsupportedOperationException("DataSource does not support loadBefore")

    override fun dispose() = disposeBag.dispose()

    override fun isDisposed() = disposeBag.isDisposed
}
