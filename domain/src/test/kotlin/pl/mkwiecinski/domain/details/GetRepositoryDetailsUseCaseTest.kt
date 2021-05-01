package pl.mkwiecinski.domain.details

import kotlinx.coroutines.test.runBlockingTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.any
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.stub
import org.mockito.kotlin.verify
import pl.mkwiecinski.domain.details.gateways.DetailsGateway
import pl.mkwiecinski.domain.listing.entities.RepositoryOwner

@RunWith(MockitoJUnitRunner::class)
internal class GetRepositoryDetailsUseCaseTest {

    @Mock
    private lateinit var owner: RepositoryOwner

    @Mock
    private lateinit var gateway: DetailsGateway

    @InjectMocks
    private lateinit var useCase: GetRepositoryDetailsUseCase

    @Test
    fun `calls gateway with proper parameters`() = runBlockingTest {
        val details = details("random_id")
        gateway.stub {
            onBlocking { getRepositoryDetails(any(), any()) } doReturn details
        }

        val result = useCase("repoName")

        assertThat(result).isEqualTo(details)
        verify(gateway).getRepositoryDetails(owner, "repoName")
    }
}
