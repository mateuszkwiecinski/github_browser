package pl.mkwiecinski.data.mocked

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import pl.mkwiecinski.domain.listing.entities.RepositoryInfo
import pl.mkwiecinski.domain.listing.entities.RepositoryOwner
import pl.mkwiecinski.domain.listing.gateways.ListingGateway
import pl.mkwiecinski.domain.listing.models.PagedResult
import javax.inject.Inject
import kotlin.time.DurationUnit
import kotlin.time.toDuration

@Suppress("MagicNumber")
internal class MockedListingGateway @Inject constructor() : ListingGateway {

    private val count = (Math.random() * 1000).toInt()

    private val all = (0..count).map {
        RepositoryInfo("$it", "Repository name $it", "https://repository/$it")
    }

    override suspend fun getFirstPage(owner: RepositoryOwner, limit: Int) =
        withContext(Dispatchers.Default) {
            randomize()
            PagedResult(all.subList(0, limit), limit.toString())
        }

    override suspend fun getPageAfter(owner: RepositoryOwner, pageKey: String, limit: Int) =
        withContext(Dispatchers.Default) {
            val current = pageKey.toInt()
            randomize()
            PagedResult(all.subList(current, current + limit), (current + limit).toString())
        }

    private suspend fun randomize() = withContext(Dispatchers.Default) {
        delay((MINIMUM_DELAY + Math.random() * DELAY_RANGE).toDuration(DurationUnit.MILLISECONDS))

        if (Math.random() < FAILURE_PROBABILITY) {
            error("Random exception")
        }
    }

    companion object {
        private const val MINIMUM_DELAY = 200
        private const val DELAY_RANGE = 5000
        private const val FAILURE_PROBABILITY = 0.2
    }
}
