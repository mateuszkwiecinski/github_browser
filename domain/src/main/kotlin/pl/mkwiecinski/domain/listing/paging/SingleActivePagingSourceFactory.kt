package pl.mkwiecinski.domain.listing.paging

import androidx.paging.DataSource
import androidx.paging.PagedList
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton
import pl.mkwiecinski.domain.base.plusAssign
import pl.mkwiecinski.domain.listing.entities.RepositoryInfo
import pl.mkwiecinski.domain.listing.persistences.InMemoryPagingEventsPersistence

interface PagingSourceFactory {

    fun getPagingList(config: PagedList.Config): Observable<PagedList<RepositoryInfo>>

    fun refresh()

    fun retry()
}

@Singleton
internal class SingleActivePagingSourceFactory @Inject constructor(
    private val pagingEvents: InMemoryPagingEventsPersistence,
    private val dataSourceProvider: Provider<RepoDataSource>,
    private val pagedListBuilder: PagedListBuilder
) : DataSource.Factory<String, RepositoryInfo>(), PagingSourceFactory {

    private val disposeBag = CompositeDisposable()
    private var currentSource: RepoDataSource? = null

    override fun create(): DataSource<String, RepositoryInfo> {
        disposeBag.clear()
        return dataSourceProvider.get().also {
            disposeBag += it
            currentSource = it
        }
    }

    override fun getPagingList(config: PagedList.Config) =
        pagedListBuilder.getPagingList(this, config)

    override fun refresh() {
        pagingEvents.onRefresh()
        currentSource?.invalidate()
    }

    override fun retry() {
        currentSource?.retry?.invoke()
    }
}
