package pl.mkwiecinski.domain.listing.models

import androidx.paging.PagedList
import io.reactivex.Observable
import pl.mkwiecinski.domain.listing.entities.RepositoryInfo

class PagingModel(
    val pagedList: Observable<PagedList<RepositoryInfo>>,
    val retry: () -> Unit,
    val networkState: Observable<LoadingState>,
    val refresh: () -> Unit,
    val refreshState: Observable<LoadingState>
)
