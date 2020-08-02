package pl.mkwiecinski.domain.listing.paging

import androidx.paging.DataSource
import androidx.paging.PagedList
import kotlinx.coroutines.flow.Flow
import pl.mkwiecinski.domain.listing.entities.RepositoryInfo
import pl.mkwiecinski.domain.listing.persistences.InMemoryPagingEventsPersistence
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

interface PagingSourceFactory {

    fun getPagingList(config: PagedList.Config): Flow<PagedList<RepositoryInfo>>

    fun refresh()

    fun retry()
}

@Singleton
internal class SingleActivePagingSourceFactory @Inject constructor(
    private val pagingEvents: InMemoryPagingEventsPersistence,
    private val dataSourceProvider: Provider<RepoDataSource>,
    private val pagedListBuilder: PagedListBuilder
) : DataSource.Factory<String, RepositoryInfo>(), PagingSourceFactory {

    private var currentSource: RepoDataSource? = null

    override fun create(): DataSource<String, RepositoryInfo> {
        currentSource?.close()
        return dataSourceProvider.get().also {
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
