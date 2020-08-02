package pl.mkwiecinski.presentation.base.paging

import androidx.lifecycle.asFlow
import androidx.paging.DataSource
import androidx.paging.PagedList
import androidx.paging.LivePagedListBuilder
import javax.inject.Inject
import pl.mkwiecinski.domain.listing.paging.PagedListBuilder

internal class AndroidPagedListBuilder @Inject constructor() : PagedListBuilder {

    override fun <TKey, TValue> getPagingList(
        factory: DataSource.Factory<TKey, TValue>,
        config: PagedList.Config
    ) = LivePagedListBuilder(factory, config)
        .build()
        .asFlow()
}
