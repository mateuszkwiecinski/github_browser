package pl.mkwiecinski.data

import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Ignore
import org.junit.Test
import pl.mkwiecinski.domain.listing.entities.RepositoryOwner

@Ignore
internal class GraphqlGatewayTest {

    private lateinit var gateway: GraphqlGateway
    private val owner = RepositoryOwner("toptal")

    @Before
    fun setUp() {
        val realApi = ConnectionModule().run {
            apollo(okHttp(setOf(AuthInterceptor(""))))
        }

        gateway = GraphqlGateway(realApi)
    }

    @Test
    fun `correctly maps first page`() {
        val test = gateway.getFirstPage(owner, 20).test()

        test.await()
            .assertNoErrors()
            .assertValue {
                it.data.size == 20
            }
    }

    @Test
    fun `combining first 2 pages should end with same result as single call with wider range`() {
        val part1 = gateway.getFirstPage(owner, 5).blockingGet()
        val part2 = gateway.getPageAfter(owner, part1.nextPageKey!!, 5).blockingGet()

        val oneShot = gateway.getFirstPage(owner, 10).blockingGet()

        assertThat(part1.data + part2.data).isEqualTo(oneShot.data)
    }

    @Test
    fun `gets repository details`() {
        val result = gateway.getRepositoryDetails(owner, "chewy").test()

        result.await()
            .assertNoErrors()
    }
}
