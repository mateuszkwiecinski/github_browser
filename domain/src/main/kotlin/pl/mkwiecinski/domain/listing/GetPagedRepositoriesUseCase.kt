package pl.mkwiecinski.domain.listing

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState
import pl.mkwiecinski.domain.listing.entities.RepositoryInfo
import pl.mkwiecinski.domain.listing.entities.RepositoryOwner
import pl.mkwiecinski.domain.listing.gateways.ListingGateway
import javax.inject.Inject
import javax.inject.Provider

class GetPagedRepositoriesUseCase @Inject constructor(private val pagingSourceFactory: Provider<RepositoriesPagingSource>) {

    operator fun invoke() = Pager(
        config = PagingConfig(pageSize = 20),
        pagingSourceFactory = pagingSourceFactory::get,
    ).flow
}

class RepositoriesPagingSource @Inject constructor(private val owner: RepositoryOwner, private val api: ListingGateway) :
    PagingSource<String, RepositoryInfo>() {

    override suspend fun load(params: LoadParams<String>): LoadResult<String, RepositoryInfo> = runCatching {
        val nextPageKey = params.key.takeIf { params is LoadParams.Append }
        val result = if (nextPageKey == null) {
            api.getFirstPage(owner, limit = params.loadSize)
        } else {
            api.getPageAfter(owner, limit = params.loadSize, pageKey = nextPageKey)
        }

        LoadResult.Page(
            data = result.data,
            prevKey = null,
            nextKey = result.nextPageKey,
        )
    }
        .getOrElse { LoadResult.Error(it) }

    override fun getRefreshKey(state: PagingState<String, RepositoryInfo>) = state.anchorPosition?.let {
        state.closestPageToPosition(it)?.nextKey
            ?: state.closestPageToPosition(it)?.prevKey
    }
}
