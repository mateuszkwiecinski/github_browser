package pl.mkwiecinski.domain.listing

import androidx.paging.PagedList
import pl.mkwiecinski.domain.listing.models.PagingModel
import pl.mkwiecinski.domain.listing.persistences.PagingEventsPersistence
import pl.mkwiecinski.domain.listing.paging.PagingSourceFactory
import javax.inject.Inject

class GetPagedRepositoriesUseCase @Inject constructor(
    private val events: PagingEventsPersistence,
    private val factory: PagingSourceFactory
) {

    operator fun invoke(config: PagedList.Config) =
        PagingModel(
            factory.getPagingList(config),
            factory::retry,
            events.networkEvents(),
            factory::refresh,
            events.refreshEvents()
        )
}
