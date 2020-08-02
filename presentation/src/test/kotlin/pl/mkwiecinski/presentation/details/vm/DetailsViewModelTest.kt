package pl.mkwiecinski.presentation.details.vm

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.stub
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.assertj.core.api.Assertions.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.internal.invocation.InterceptedInvocation
import org.mockito.invocation.InvocationOnMock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.stubbing.OngoingStubbing
import pl.mkwiecinski.domain.details.GetRepositoryDetailsUseCase
import pl.mkwiecinski.domain.details.entities.RepositoryDetails
import kotlin.coroutines.Continuation
import kotlin.coroutines.intrinsics.startCoroutineUninterceptedOrReturn

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
internal class DetailsViewModelTest {

    @get:Rule
    val liveDataRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var usecase: GetRepositoryDetailsUseCase

    private lateinit var viewModel: DetailsViewModel

    private val emitter = BroadcastChannel<RepositoryDetails>(capacity = 1)

    @Before
    fun setUp() {
        Dispatchers.setMain(Dispatchers.Unconfined)
        usecase.stub {
            onBlocking { invoke(any()) } willAnswer { emitter.asFlow().first() }
        }
        viewModel = DetailsViewModel("repositoryName", usecase)
        viewModel.details.observeForever { }
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `shows progress on first load`() = runBlockingTest {
        assertThat(viewModel.isLoading.value).isTrue()

        emitter.send(details("1"))

        assertThat(viewModel.isLoading.value).isFalse()
        verify(usecase).invoke(any())
    }

    @Test
    fun `shows progress on subsequent load`() = runBlockingTest {
        emitter.send(details("1")) // first load

        viewModel.retry()
        assertThat(viewModel.isLoading.value).isTrue()
        emitter.send(details("1"))

        assertThat(viewModel.isLoading.value).isFalse()
        verify(usecase, times(2)).invoke(any())
    }

    @Test
    fun `shows error on first load`() = runBlockingTest {
        assertThat(viewModel.error.value).isNull()

        emitter.close(IllegalStateException())

        assertThat(viewModel.error.value).isInstanceOf(IllegalStateException::class.java)
        verify(usecase).invoke(any())
    }

    @Test
    fun `shows error on subsequent load`() = runBlockingTest {
        emitter.send(details("1")) // first load

        viewModel.retry()
        assertThat(viewModel.error.value).isNull()
        emitter.close(IllegalStateException())

        assertThat(viewModel.error.value).isInstanceOf(IllegalStateException::class.java)
        verify(usecase, times(2)).invoke(any())
    }
}

@Suppress("UNCHECKED_CAST")
infix fun <T> OngoingStubbing<T>.willAnswer(answer: suspend (InvocationOnMock) -> T?): OngoingStubbing<T> {
    return thenAnswer {
        // all suspend functions/lambdas has Continuation as the last argument.
        // InvocationOnMock does not see last argument
        val rawInvocation = it as InterceptedInvocation
        val continuation = rawInvocation.rawArguments.last() as Continuation<T?>

        answer.startCoroutineUninterceptedOrReturn(it, continuation)
    }
}
