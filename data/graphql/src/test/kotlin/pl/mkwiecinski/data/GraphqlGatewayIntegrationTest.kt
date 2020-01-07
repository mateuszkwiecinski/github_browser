package pl.mkwiecinski.data

import java.io.File
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import pl.mkwiecinski.data.di.ConnectionModule
import pl.mkwiecinski.data.di.GithubConfig
import pl.mkwiecinski.domain.listing.entities.RepositoryOwner

internal class GraphqlGatewayIntegrationTest {

    private lateinit var gateway: GraphqlGateway
    private val owner = RepositoryOwner("toptal")

    private val server = MockWebServer()

    @Before
    fun setUp() {
        val apolloClient = ConnectionModule().run {
            apollo(okHttp(emptySet()), GithubConfig(server.url("/").toString(), "dummyToken"))
        }
        gateway = GraphqlGateway(apolloClient)
    }

    @Test
    fun `correctly maps first page`() {
        server.enqueue(mockJson("repositories.json"))

        val test = gateway.getFirstPage(owner, 20).test()

        test.await()
            .assertNoErrors()
            .assertValue {
                it.data.size == 20
            }
    }

    @Test
    fun `combining first 2 pages should end with same result as single call with wider range`() {
        server.enqueue(mockJson("page1.json"))
        server.enqueue(mockJson("page2.json"))
        server.enqueue(mockJson("page1+2.json"))
        val part1 = gateway.getFirstPage(owner, 5).blockingGet()
        val part2 = gateway.getPageAfter(owner, part1.nextPageKey!!, 5).blockingGet()

        val oneShot = gateway.getFirstPage(owner, 10).blockingGet()

        assertThat(part1.data + part2.data).isEqualTo(oneShot.data)
    }

    @Test
    fun `gets repository details`() {
        server.enqueue(mockJson("details.json"))
        val result = gateway.getRepositoryDetails(owner, "dummyName").test()

        result.await()
            .assertNoErrors()
            .assertValue {
                it.id == "MDEwOlJlcG9zaXRvcnkxMDYyODk3"
            }
    }

    private fun mockJson(fileName: String): MockResponse {
        val file = File(javaClass.classLoader.getResource(fileName).file)
        return MockResponse().apply {
            setBody(file.readText())
        }
    }
}
