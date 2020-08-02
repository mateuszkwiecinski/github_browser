package pl.mkwiecinski.domain.listing.paging

import androidx.paging.PageKeyedDataSource
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doAnswer
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.doThrow
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.never
import com.nhaarman.mockitokotlin2.stub
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.suspendCancellableCoroutine
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.internal.invocation.InterceptedInvocation
import org.mockito.invocation.InvocationOnMock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.stubbing.OngoingStubbing
import pl.mkwiecinski.domain.details.info
import pl.mkwiecinski.domain.listing.entities.RepositoryInfo
import pl.mkwiecinski.domain.listing.entities.RepositoryOwner
import pl.mkwiecinski.domain.listing.gateways.ListingGateway
import pl.mkwiecinski.domain.listing.models.PagedResult
import pl.mkwiecinski.domain.listing.persistences.InMemoryPagingEventsPersistence
import kotlin.coroutines.Continuation
import kotlin.coroutines.intrinsics.startCoroutineUninterceptedOrReturn

@RunWith(MockitoJUnitRunner::class)
internal class RepoDataSourceTest {

    @Mock
    private lateinit var gateway: ListingGateway

    @Mock
    private lateinit var events: InMemoryPagingEventsPersistence

    @Mock
    private lateinit var owner: RepositoryOwner

    private lateinit var dataSource: RepoDataSource

    lateinit var data: List<RepositoryInfo>

    @Before
    fun setUp() {
        dataSource = RepoDataSource(
            gateway = gateway,
            events = events,
            repositoryOwner = owner,
            dispatcher = Dispatchers.Unconfined
        )
        data = listOf(info("1"), info("2"), info("3"))
    }

    @Test
    fun `loads initial data`() {
        gateway.stub {
            onBlocking { getFirstPage(any(), any()) } doReturn PagedResult(data, "nextPage")
        }
        val callback = mock<InitialCallback>()

        dataSource.loadInitial(initial(), callback)

        verify(callback).onResult(data, null, "nextPage")
        verify(events).onLoadSuccessful()
    }

    @Test
    fun `sets proper state if loading initial data failed`() {
        gateway.stub {
            onBlocking { getFirstPage(any(), any()) } doThrow IllegalStateException()
        }
        val callback = mock<InitialCallback>()

        dataSource.loadInitial(initial(), callback)

        verify(callback, never()).onResult(any(), any(), any())
        verify(events).onLoadError()
    }

    @Test
    fun `retries initial call if loading initial failed`() {
        var responseNumber = 0
        gateway.stub {
            onBlocking {
                getFirstPage(
                    any(),
                    any()
                )
            }.doAnswer {
                if (responseNumber++ == 0) {
                    throw IllegalStateException()
                } else {
                    PagedResult(data, "nextPage")
                }
            }
        }
        val callback = mock<InitialCallback>()
        dataSource.loadInitial(initial(), callback)
        verify(callback, never()).onResult(any(), any(), any())

        dataSource.retry()

        verify(callback).onResult(data, null, "nextPage")
    }

    @Test
    fun `disposes initial data call when whole source is being disposed`() {
        var disposeCalled = false
        gateway.stub {
            onBlocking { getFirstPage(any(), any()) } willAnswer {
                suspendCancellableCoroutine {
                    it.invokeOnCancellation { disposeCalled = true }
                }
            }
        }
        dataSource.loadInitial(initial(), mock())

        dataSource.close()

        assertThat(disposeCalled).isTrue()
    }

    @Test
    fun `loads after data`() {
        gateway.stub {
            onBlocking { getPageAfter(any(), any(), any()) } doReturn PagedResult(data, "nextPage")
        }
        val callback = mock<AfterCallback>()

        dataSource.loadAfter(after("currentPage"), callback)

        verify(callback).onResult(data, "nextPage")
        verify(events).onLoadSuccessful()
    }

    @Test
    fun `sets proper state if loading after data failed`() {
        gateway.stub {
            onBlocking { getPageAfter(any(), any(), any()) } doThrow IllegalStateException()
        }
        val callback = mock<AfterCallback>()

        dataSource.loadAfter(after("currentPage"), callback)

        verify(callback, never()).onResult(any(), any())
        verify(events).onLoadError()
    }

    @Test
    fun `disposes after data call when whole source is being disposed`() {
        var disposeCalled = false
        gateway.stub {
            onBlocking { getPageAfter(any(), any(), any()) } willAnswer {
                suspendCancellableCoroutine {
                    it.invokeOnCancellation { disposeCalled = true }
                }
            }
        }
        dataSource.loadAfter(after("currentPage"), mock())

        dataSource.close()

        assertThat(disposeCalled).isTrue()
    }

    @Test
    fun `retries after call if after call failed`() {
        var responseNumber = 0
        gateway.stub {
            onBlocking { getPageAfter(any(), any(), any()) }.doAnswer {
                if (responseNumber++ == 0) {
                    throw IllegalStateException()
                } else {
                    PagedResult(data, "nextPage")
                }
            }
        }
        val callback = mock<AfterCallback>()
        dataSource.loadAfter(after("currentPage"), callback)
        verify(callback, never()).onResult(any(), any())

        dataSource.retry()

        verify(callback).onResult(data, "nextPage")
    }

    private fun initial(limit: Int = 10) =
        PageKeyedDataSource.LoadInitialParams<String>(limit, false)

    private fun after(key: String, limit: Int = 10) =
        PageKeyedDataSource.LoadParams(key, limit)
}

private typealias InitialCallback = PageKeyedDataSource.LoadInitialCallback<String, RepositoryInfo>
private typealias AfterCallback = PageKeyedDataSource.LoadCallback<String, RepositoryInfo>

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
