package pl.mkwiecinski.data

import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Ignore
import org.junit.Test

@Ignore
internal class GraphqlGatewayTest {

    private lateinit var gateway: GraphqlGateway
    @Before
    fun setUp() {
        val realApi = ConnectionModule().run {
            apollo(okHttp(setOf(AuthInterceptor())))
        }

        gateway = GraphqlGateway(realApi)
    }

    @Test
    fun `correctly maps first page`() {
        val test = gateway.getFirstPage("toptal", 20).test()

        test.await()
            .assertNoErrors()
            .assertValue {
                it.data.size == 20
            }
    }

    @Test
    fun `combining first 2 pages should end with same result as single call with wider range`() {
        val part1 = gateway.getFirstPage("toptal", 5).blockingGet()
        val part2 = gateway.getPageAfter("toptal", part1.nextPageKey, 5).blockingGet()

        val oneShot = gateway.getFirstPage("toptal", 10).blockingGet()

        assertThat(part1.data + part2.data).isEqualTo(oneShot.data)
    }
}
