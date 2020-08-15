package pl.mkwiecinski.presentation.base.paging

import androidx.lifecycle.asFlow
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import pl.mkwiecinski.domain.listing.paging.PagedListBuilder
import javax.inject.Inject

internal class AndroidPagedListBuilder @Inject constructor() : PagedListBuilder {

    override fun <TKey, TValue> getPagingList(
        factory: DataSource.Factory<TKey, TValue>,
        config: PagedList.Config
    ) = LivePagedListBuilder(factory, config)
        .build()
        .asFlow()
}
