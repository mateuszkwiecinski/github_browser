package pl.mkwiecinski.domain.listing.models

import androidx.paging.PagedList
import kotlinx.coroutines.flow.Flow
import pl.mkwiecinski.domain.listing.entities.RepositoryInfo

class PagingModel(
    val pagedList: Flow<PagedList<RepositoryInfo>>,
    val retry: () -> Unit,
    val networkState: Flow<LoadingState>,
    val refresh: () -> Unit,
    val refreshState: Flow<LoadingState>
)
