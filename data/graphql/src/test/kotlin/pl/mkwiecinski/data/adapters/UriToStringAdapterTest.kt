package pl.mkwiecinski.data.adapters

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class UriToStringAdapterTest {

    @Test
    fun `serialized and deserializes value`() {
        val encoded = UriToStringAdapter.encode("this is a test;")

        val decoded = UriToStringAdapter.decode(encoded)

        assertThat(decoded).isEqualTo("this is a test;")
    }
}
