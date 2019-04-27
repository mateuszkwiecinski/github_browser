package pl.mkwiecinski.presentation.list.vm

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import pl.mkwiecinski.domain.PagingUseCase
import pl.mkwiecinski.domain.entities.RepositoryInfo
import pl.mkwiecinski.presentation.base.BaseViewModel
import javax.inject.Inject

internal class ListViewModel @Inject constructor(
    private val paging: PagingUseCase
) : BaseViewModel() {

    val refreshState = paging.getRefreshState().toLiveData()
    val networkState = paging.getNetworkState().toLiveData()

    val repositories: LiveData<PagedList<RepositoryInfo>>

    init {
        val config = PagedList.Config.Builder().apply {
            setInitialLoadSizeHint(10)
            setPageSize(10)
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
}
