package pl.mkwiecinski.domain.listing.paging

import androidx.paging.DataSource
import io.reactivex.disposables.CompositeDisposable
import pl.mkwiecinski.domain.listing.PagingUseCase
import pl.mkwiecinski.domain.listing.entities.RepositoryInfo
import pl.mkwiecinski.domain.listing.gateways.ListingGateway
import pl.mkwiecinski.domain.listing.entities.RepositoryOwner
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class SingleActiveDataSourceFactory @Inject constructor(
    private val gateway: ListingGateway,
    private val pagingEvents: InMemoryPagingEvents,
    private val owner: RepositoryOwner
) : DataSource.Factory<String, RepositoryInfo>(), PagingUseCase {

    private val disposeBag = CompositeDisposable()
    private var currentSource: RepoDataSource? = null

    override fun create(): DataSource<String, RepositoryInfo> {
        disposeBag.clear()
        return RepoDataSource(gateway, pagingEvents, disposeBag, owner).also {
            currentSource = it
        }
    }

    override fun getDataFactory() = this

    override fun retry() {
        currentSource?.retry?.invoke()
    }

    override fun refresh() {
        pagingEvents.onRefresh()
        currentSource?.invalidate()
    }

    override fun getRefreshState() = pagingEvents.refreshEvents()

    override fun getNetworkState() = pagingEvents.networkEvents()
}
