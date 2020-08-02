package pl.mkwiecinski.data

import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import pl.mkwiecinski.data.di.DaggerNetworkingComponent
import pl.mkwiecinski.data.di.GithubConfig
import pl.mkwiecinski.data.di.NetworkingComponent
import pl.mkwiecinski.domain.details.gateways.DetailsGateway
import pl.mkwiecinski.domain.listing.entities.RepositoryOwner
import pl.mkwiecinski.domain.listing.gateways.ListingGateway
import java.io.File

internal class GraphqlGatewayIntegrationTest {

    private val owner = RepositoryOwner("toptal")
    private lateinit var details: DetailsGateway
    private lateinit var listing: ListingGateway

    private val server = MockWebServer()
    private val testCoroutineDispatcher = TestCoroutineDispatcher()

    @Before
    fun setUp() {
        val component: NetworkingComponent = DaggerNetworkingComponent.factory()
            .create(
                config = GithubConfig(server.url("/").toString(), "dummyToken"),
                dispatcher = testCoroutineDispatcher
            )
        details = component.details()
        listing = component.listing()
    }

    @Test
    fun `correctly maps first page`() {
        runBlocking(testCoroutineDispatcher) {
            server.enqueue(mockJson("repositories.json"))

            val result = listing.getFirstPage(owner, 20)

            assertThat(result.data).hasSize(20)
        }
    }

    @Test
    fun `combining first 2 pages should end with same result as single call with wider range`() {
        runBlocking(testCoroutineDispatcher) {
            server.enqueue(mockJson("page1.json"))
            server.enqueue(mockJson("page2.json"))
            server.enqueue(mockJson("page1+2.json"))
            val part1 = listing.getFirstPage(owner, 5)
            val part2 = listing.getPageAfter(owner, part1.nextPageKey!!, 5)

            val oneShot = listing.getFirstPage(owner, 10)

            assertThat(part1.data + part2.data).isEqualTo(oneShot.data)
        }
    }

    @Test
    fun `gets repository details`() {
        runBlocking(testCoroutineDispatcher) {
            server.enqueue(mockJson("details.json"))
            val result = details.getRepositoryDetails(owner, "dummyName")

            assertThat(result.id).isEqualTo("MDEwOlJlcG9zaXRvcnkxMDYyODk3")
        }
    }

    private fun mockJson(fileName: String): MockResponse {
        val file = File(javaClass.classLoader.getResource(fileName).file)
        return MockResponse().apply {
            setBody(file.readText())
        }
    }
}
