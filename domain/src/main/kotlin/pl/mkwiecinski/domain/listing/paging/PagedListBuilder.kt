package pl.mkwiecinski.domain.listing.paging

import androidx.paging.DataSource
import androidx.paging.PagedList
import kotlinx.coroutines.flow.Flow

interface PagedListBuilder {

    fun <TKey, TItem> getPagingList(
        factory: DataSource.Factory<TKey, TItem>,
        config: PagedList.Config
    ): Flow<PagedList<TItem>>
}
