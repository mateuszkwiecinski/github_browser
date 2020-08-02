package pl.mkwiecinski.domain.details

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.stub
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.test.runBlockingTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
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
