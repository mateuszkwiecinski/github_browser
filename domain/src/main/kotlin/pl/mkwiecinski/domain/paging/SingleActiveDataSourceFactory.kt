package pl.mkwiecinski.domain.paging

import androidx.paging.DataSource
import io.reactivex.disposables.CompositeDisposable
import pl.mkwiecinski.domain.PagingUseCase
import pl.mkwiecinski.domain.entities.RepositoryInfo
import pl.mkwiecinski.domain.gateways.RepoGateway
import pl.mkwiecinski.domain.paging.models.RepositoryOwner
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class SingleActiveDataSourceFactory @Inject constructor(
    private val gateway: RepoGateway,
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
