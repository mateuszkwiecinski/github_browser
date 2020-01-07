package pl.mkwiecinski.mocked

import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import pl.mkwiecinski.domain.listing.entities.RepositoryInfo
import pl.mkwiecinski.domain.listing.entities.RepositoryOwner
import pl.mkwiecinski.domain.listing.gateways.ListingGateway
import pl.mkwiecinski.domain.listing.models.PagedResult

@Suppress("MagicNumber")
internal class MockedListingGateway @Inject constructor() : ListingGateway {

    private val count = (Math.random() * 1000).toInt()

    private val all = (0..count).map {
        RepositoryInfo("$it", "Repository name $it", "https://repository/$it")
    }

    override fun getFirstPage(owner: RepositoryOwner, limit: Int) =
        Single.fromCallable {
            PagedResult(all.subList(0, limit), limit.toString())
        }
            .subscribeOn(Schedulers.io())
            .randomize()

    override fun getPageAfter(owner: RepositoryOwner, pageKey: String, limit: Int) =
        Single.fromCallable {
            val current = pageKey.toInt()
            PagedResult(all.subList(current, current + limit), (current + limit).toString())
        }
            .subscribeOn(Schedulers.io())
            .randomize()

    private fun <T> Single<T>.randomize(): Single<T> {
        val delay = MINIMUM_DELAY + Math.random() * DELAY_RANGE

        return delay(delay.toLong(), TimeUnit.MILLISECONDS)
            .flatMap {
                if (Math.random() > FAILURE_PROBABILITY) {
                    Single.just(it)
                } else {
                    Single.error(IllegalStateException("Random exception"))
                }
            }
    }

    companion object {
        private const val MINIMUM_DELAY = 200
        private const val DELAY_RANGE = 5000
        private const val FAILURE_PROBABILITY = 0.2
    }
}
