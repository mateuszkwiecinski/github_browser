package pl.mkwiecinski.presentation.details.vm

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.stub
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import io.reactivex.Single
import io.reactivex.SingleEmitter
import java.lang.IllegalStateException
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import pl.mkwiecinski.domain.details.GetRepositoryDetails
import pl.mkwiecinski.domain.details.entities.RepositoryDetails

@RunWith(MockitoJUnitRunner::class)
internal class DetailsViewModelTest {

    @get:Rule
    val liveDataRule = InstantTaskExecutorRule()

    @get:Rule
    val rxRule = RxImmediateRule()

    @Mock
    private lateinit var usecase: GetRepositoryDetails

    private lateinit var viewModel: DetailsViewModel

    private lateinit var emitter: SingleEmitter<RepositoryDetails>

    @Before
    fun setUp() {
        usecase.stub {
            on { invoke(any()) } doReturn Single.create { emitter = it }
        }
        viewModel = DetailsViewModel("repositoryName", usecase)
    }

    @Test
    fun `shows progress on first load`() {
        assertThat(viewModel.isLoading.value).isTrue()

        emitter.onSuccess(details("1"))

        assertThat(viewModel.isLoading.value).isFalse()
        verify(usecase).invoke(any())
    }

    @Test
    fun `shows progress on subsequent load`() {
        emitter.onSuccess(details("1")) // first load

        viewModel.retry()
        assertThat(viewModel.isLoading.value).isTrue()
        emitter.onSuccess(details("1"))

        assertThat(viewModel.isLoading.value).isFalse()
        verify(usecase, times(2)).invoke(any())
    }

    @Test
    fun `shows error on first load`() {
        assertThat(viewModel.error.value).isNull()

        emitter.onError(IllegalStateException())

        assertThat(viewModel.error.value).isInstanceOf(IllegalStateException::class.java)
        verify(usecase).invoke(any())
    }

    @Test
    fun `shows error on subsequent load`() {
        emitter.onSuccess(details("1")) // first load

        viewModel.retry()
        assertThat(viewModel.error.value).isNull()
        emitter.onError(IllegalStateException())

        assertThat(viewModel.error.value).isInstanceOf(IllegalStateException::class.java)
        verify(usecase, times(2)).invoke(any())
    }
}
