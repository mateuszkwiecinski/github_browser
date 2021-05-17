package pl.mkwiecinski.presentation.details.vm

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
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
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.any
import org.mockito.kotlin.doSuspendableAnswer
import org.mockito.kotlin.stub
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import pl.mkwiecinski.domain.details.GetRepositoryDetailsUseCase
import pl.mkwiecinski.domain.details.entities.RepositoryDetails

@RunWith(MockitoJUnitRunner::class)
internal class DetailsViewModelTest {

    @get:Rule
    val liveDataRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var usecase: GetRepositoryDetailsUseCase

    private lateinit var viewModel: DetailsViewModel

    private val emitter = MutableSharedFlow<Result<RepositoryDetails>>()

    @Before
    fun setUp() {
        Dispatchers.setMain(Dispatchers.Unconfined)
        usecase.stub {
            onBlocking { invoke(any()) } doSuspendableAnswer { emitter.first().getOrThrow() }
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

        emitter.emit(Result.success(details("1")))

        assertThat(viewModel.isLoading.value).isFalse()
        verify(usecase).invoke(any())
    }

    @Test
    fun `shows progress on subsequent load`() = runBlockingTest {
        emitter.emit(Result.success(details("1"))) // first load

        viewModel.retry()
        assertThat(viewModel.isLoading.value).isTrue()
        emitter.emit(Result.success(details("1")))

        assertThat(viewModel.isLoading.value).isFalse()
        verify(usecase, times(2)).invoke(any())
    }

    @Test
    fun `shows error on first load`() = runBlockingTest {
        assertThat(viewModel.error.value).isNull()

        emitter.emit(Result.failure(IllegalStateException()))

        assertThat(viewModel.error.value).isInstanceOf(IllegalStateException::class.java)
        verify(usecase).invoke(any())
    }

    @Test
    fun `shows error on subsequent load`() = runBlockingTest {
        emitter.emit(Result.success(details("1"))) // first load

        viewModel.retry()
        assertThat(viewModel.error.value).isNull()
        emitter.emit(Result.failure(IllegalStateException()))

        assertThat(viewModel.error.value).isInstanceOf(IllegalStateException::class.java)
        verify(usecase, times(2)).invoke(any())
    }
}
