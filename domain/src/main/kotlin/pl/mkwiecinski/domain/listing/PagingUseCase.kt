package pl.mkwiecinski.domain.listing

import androidx.paging.DataSource
import io.reactivex.Observable
import pl.mkwiecinski.domain.listing.entities.RepositoryInfo
import pl.mkwiecinski.domain.listing.models.LoadingState

interface PagingUseCase {

    fun getDataFactory(): DataSource.Factory<String, RepositoryInfo>

    fun retry()

    fun getNetworkState(): Observable<LoadingState>

    fun refresh()

    fun getRefreshState(): Observable<LoadingState>
}
