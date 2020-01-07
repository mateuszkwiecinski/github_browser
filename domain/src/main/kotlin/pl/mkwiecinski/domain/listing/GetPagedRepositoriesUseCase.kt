package pl.mkwiecinski.domain.listing

import androidx.paging.PagedList
import javax.inject.Inject
import pl.mkwiecinski.domain.listing.models.PagingModel
import pl.mkwiecinski.domain.listing.paging.PagingSourceFactory
import pl.mkwiecinski.domain.listing.persistences.PagingEventsPersistence

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
