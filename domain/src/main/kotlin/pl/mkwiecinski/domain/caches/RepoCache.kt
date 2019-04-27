package pl.mkwiecinski.domain.caches

import io.reactivex.Completable
import pl.mkwiecinski.domain.paging.models.PagedResult
import pl.mkwiecinski.domain.entities.RepositoryInfo

interface RepoCache {

    fun upsert(data: PagedResult<RepositoryInfo>): Completable
}
