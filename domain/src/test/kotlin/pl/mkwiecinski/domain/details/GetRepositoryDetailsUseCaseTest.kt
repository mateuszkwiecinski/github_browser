package pl.mkwiecinski.domain.details

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.stub
import com.nhaarman.mockitokotlin2.verify
import io.reactivex.Single
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
    fun `calls gateway with proper parameters`() {
        val details = details("random_id")
        gateway.stub {
            on { getRepositoryDetails(any(), any()) } doReturn Single.just(details)
        }

        val result = useCase("repoName").test()

        result.await()
            .assertNoErrors()
            .assertValue { it == details }
        verify(gateway).getRepositoryDetails(owner, "repoName")
    }
}
