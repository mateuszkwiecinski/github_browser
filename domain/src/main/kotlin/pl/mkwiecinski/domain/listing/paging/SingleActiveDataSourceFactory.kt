package pl.mkwiecinski.domain.listing.paging

import androidx.paging.DataSource
import io.reactivex.disposables.CompositeDisposable
import pl.mkwiecinski.domain.base.plusAssign
import pl.mkwiecinski.domain.listing.PagingUseCase
import pl.mkwiecinski.domain.listing.entities.RepositoryInfo
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

@Singleton
internal class SingleActiveDataSourceFactory @Inject constructor(
    private val pagingEvents: InMemoryPagingEvents,
    private val dataSourceProvider: Provider<RepoDataSource>
) : DataSource.Factory<String, RepositoryInfo>(), PagingUseCase {

    private val disposeBag = CompositeDisposable()
    private var currentSource: RepoDataSource? = null

    override fun create(): DataSource<String, RepositoryInfo> {
        disposeBag.clear()
        return dataSourceProvider.get().also {
            disposeBag += it
            currentSource = it
        }
    }

    override fun getDataFactory(): DataSource.Factory<String, RepositoryInfo> =
        this

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
