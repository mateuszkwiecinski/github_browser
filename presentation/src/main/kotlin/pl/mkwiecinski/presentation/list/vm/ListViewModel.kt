package pl.mkwiecinski.presentation.list.vm

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import pl.mkwiecinski.domain.listing.PagingUseCase
import pl.mkwiecinski.domain.listing.entities.RepositoryInfo
import pl.mkwiecinski.presentation.base.BaseViewModel
import javax.inject.Inject

internal class ListViewModel @Inject constructor(
    private val paging: PagingUseCase
) : BaseViewModel() {

    /**
     * For better UX, the network progress could be hidden in case when user swipes to refresh and additional progress is being shown.
     * At this moment there are 2 progresses visible at the same time. I decided to leave it that way due to lack time.
     */
    val refreshState = paging.getRefreshState().toLiveData()
    val networkState = paging.getNetworkState().toLiveData()

    val repositories: LiveData<PagedList<RepositoryInfo>>

    init {
        val config = PagedList.Config.Builder().apply {
            setInitialLoadSizeHint(INITIAL_PAGE_SIZE)
            setPageSize(DEFAULT_PAGE_SIZE)
            setEnablePlaceholders(true)
        }.build()

        repositories = LivePagedListBuilder(paging.getDataFactory(), config)
            .build()
    }

    fun retryFailed() {
        paging.retry()
    }

    fun refresh() {
        paging.refresh()
    }

    companion object {
        private const val INITIAL_PAGE_SIZE = 10
        private const val DEFAULT_PAGE_SIZE = 10
    }
}
