package pl.mkwiecinski.presentation.list.vm

import androidx.paging.PagedList
import pl.mkwiecinski.domain.listing.PagingUseCase
import pl.mkwiecinski.presentation.base.BaseViewModel
import javax.inject.Inject

internal class ListViewModel @Inject constructor(
    getPaging: PagingUseCase
) : BaseViewModel() {

    private val pagingConfig = PagedList.Config.Builder().apply {
        setInitialLoadSizeHint(INITIAL_PAGE_SIZE)
        setPageSize(DEFAULT_PAGE_SIZE)
    }.build()
    private val listPaging = getPaging(pagingConfig)

    /**
     * For better UX, the network progress could be hidden in case when user swipes to refresh and additional progress is being shown.
     * At this moment there are 2 progresses visible at the same time. I decided to leave it that way due to lack of time.
     */
    val refreshState = listPaging.refreshState.toLiveData()
    val networkState = listPaging.networkState.toLiveData()

    val repositories = listPaging.pagedList.toLiveData()

    fun retryFailed() {
        listPaging.retry()
    }

    fun refresh() {
        listPaging.refresh()
    }

    companion object {

        private const val INITIAL_PAGE_SIZE = 10
        private const val DEFAULT_PAGE_SIZE = 10
    }
}
