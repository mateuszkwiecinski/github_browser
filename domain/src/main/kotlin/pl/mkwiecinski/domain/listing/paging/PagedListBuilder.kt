package pl.mkwiecinski.domain.listing.paging

import androidx.paging.DataSource
import androidx.paging.PagedList
import io.reactivex.Observable

interface PagedListBuilder {

    fun <TKey, TItem> getPagingList(
        factory: DataSource.Factory<TKey, TItem>,
        config: PagedList.Config
    ): Observable<PagedList<TItem>>
}
