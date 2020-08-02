package pl.mkwiecinski.data.mocked

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import java.util.UUID
import javax.inject.Inject
import pl.mkwiecinski.domain.details.entities.IssuePreview
import pl.mkwiecinski.domain.details.entities.IssuesInfo
import pl.mkwiecinski.domain.details.entities.PullRequestsInfo
import pl.mkwiecinski.domain.details.entities.RepositoryDetails
import pl.mkwiecinski.domain.details.gateways.DetailsGateway
import pl.mkwiecinski.domain.listing.entities.RepositoryOwner

@Suppress("MagicNumber")
internal class MockedDetailsGateway @Inject constructor() : DetailsGateway {

    override suspend fun getRepositoryDetails(owner: RepositoryOwner, name: String) =
        withContext(Dispatchers.Default) {
            delay(1000)
            val issues = IssuesInfo(
                openedTotalCount = 50,
                openedPreview = listOf(issue(1), issue(4)),
                closedTotalCount = 2,
                closedPreview = listOf(issue(2), issue(3))
            )
            RepositoryDetails(
                id = UUID.randomUUID().toString(),
                name = UUID.randomUUID().toString(),
                url = "https://example.com",
                issues = issues,
                pullRequests = PullRequestsInfo(0, emptyList(), 0, emptyList())
            )
        }

    private fun issue(number: Int) = IssuePreview(
        id = number.toString(),
        name = "Random name",
        number = number,
        url = "https://issue/$number"
    )
}
