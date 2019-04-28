package pl.mkwiecinski.domain.listing.paging

import androidx.paging.PageKeyedDataSource
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.never
import com.nhaarman.mockitokotlin2.stub
import com.nhaarman.mockitokotlin2.verify
import io.reactivex.Single
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import pl.mkwiecinski.domain.details.info
import pl.mkwiecinski.domain.listing.entities.RepositoryInfo
import pl.mkwiecinski.domain.listing.entities.RepositoryOwner
import pl.mkwiecinski.domain.listing.gateways.ListingGateway
import pl.mkwiecinski.domain.listing.models.PagedResult

@RunWith(MockitoJUnitRunner::class)
internal class RepoDataSourceTest {

    @Mock
    private lateinit var gateway: ListingGateway
    @Mock
    private lateinit var events: InMemoryPagingEvents
    @Mock
    private lateinit var owner: RepositoryOwner

    @InjectMocks
    private lateinit var dataSource: RepoDataSource

    lateinit var data: List<RepositoryInfo>

    @Before
    fun setUp() {
        data = listOf(info("1"), info("2"), info("3"))
    }

    @Test
    fun `loads initial data`() {
        gateway.stub {
            on { getFirstPage(any(), any()) } doReturn Single.just(PagedResult(data, "nextPage"))
        }
        val callback = mock<InitialCallback>()

        dataSource.loadInitial(initial(), callback)

        verify(callback).onResult(data, null, "nextPage")
        verify(events).onLoadSuccessful()
    }

    @Test
    fun `sets proper state if loading initial data failed`() {
        gateway.stub {
            on { getFirstPage(any(), any()) } doReturn Single.error(IllegalStateException())
        }
        val callback = mock<InitialCallback>()

        dataSource.loadInitial(initial(), callback)

        verify(callback, never()).onResult(any(), any(), any())
        verify(events).onLoadError()
    }

    @Test
    fun `retries initial call if loading initial failed`() {
        gateway.stub {
            on { getFirstPage(any(), any()) }.doReturn(Single.error(IllegalStateException()), Single.just(PagedResult(data, "nextPage")))
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
        val call = Single.never<PagedResult<RepositoryInfo>>().doOnDispose { disposeCalled = true }
        gateway.stub {
            on { getFirstPage(any(), any()) } doReturn call
        }
        dataSource.loadInitial(initial(), mock())

        dataSource.dispose()

        assertThat(disposeCalled).isTrue()
    }

    @Test
    fun `loads after data`() {
        gateway.stub {
            on { getPageAfter(any(), any(), any()) } doReturn Single.just(PagedResult(data, "nextPage"))
        }
        val callback = mock<AfterCallback>()

        dataSource.loadAfter(after("currentPage"), callback)

        verify(callback).onResult(data, "nextPage")
        verify(events).onLoadSuccessful()
    }

    @Test
    fun `sets proper state if loading after data failed`() {
        gateway.stub {
            on { getPageAfter(any(), any(), any()) } doReturn Single.error(IllegalStateException())
        }
        val callback = mock<AfterCallback>()

        dataSource.loadAfter(after("currentPage"), callback)

        verify(callback, never()).onResult(any(), any())
        verify(events).onLoadError()
    }

    @Test
    fun `disposes after data call when whole source is being disposed`() {
        var disposeCalled = false
        val call = Single.never<PagedResult<RepositoryInfo>>().doOnDispose { disposeCalled = true }
        gateway.stub {
            on { getPageAfter(any(), any(), any()) } doReturn call
        }
        dataSource.loadAfter(after("currentPage"), mock())

        dataSource.dispose()

        assertThat(disposeCalled).isTrue()
    }

    @Test
    fun `retries after call if after call failed`() {
        gateway.stub {
            on { getPageAfter(any(), any(), any()) }.doReturn(
                Single.error(IllegalStateException()),
                Single.just(PagedResult(data, "nextPage"))
            )
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
