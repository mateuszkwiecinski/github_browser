package pl.mkwiecinski.domain

import androidx.paging.DataSource
import io.reactivex.Observable
import pl.mkwiecinski.domain.entities.RepositoryInfo
import pl.mkwiecinski.domain.paging.models.LoadingState

interface PagingUseCase {

    fun getDataFactory(): DataSource.Factory<String, RepositoryInfo>

    fun retry()

    fun getNetworkState(): Observable<LoadingState>

    fun refresh()

    fun getRefreshState(): Observable<LoadingState>
}
