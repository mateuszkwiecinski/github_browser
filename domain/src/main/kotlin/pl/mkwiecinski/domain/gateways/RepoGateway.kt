package pl.mkwiecinski.domain.gateways

import io.reactivex.Single
import pl.mkwiecinski.domain.entities.RepositoryInfo
import pl.mkwiecinski.domain.paging.models.PagedResult

interface RepoGateway {

    fun getFirstPage(owner: String, limit: Int): Single<PagedResult<RepositoryInfo>>

    fun getPageAfter(owner: String, pageKey: String, limit: Int): Single<PagedResult<RepositoryInfo>>
}
