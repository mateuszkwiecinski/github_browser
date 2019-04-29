package pl.mkwiecinski.domain.listing.paging

import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.never
import com.nhaarman.mockitokotlin2.stub
import com.nhaarman.mockitokotlin2.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import javax.inject.Provider

@RunWith(MockitoJUnitRunner::class)
internal class SingleActiveDataSourceFactoryTest {

    @Mock
    private lateinit var pagingEvents: InMemoryPagingEvents
    @Mock
    private lateinit var dataSourceProvider: Provider<RepoDataSource>

    @Mock
    private lateinit var firstSource: RepoDataSource

    @Mock
    private lateinit var secondSource: RepoDataSource

    @InjectMocks
    private lateinit var usecase: SingleActiveDataSourceFactory

    @Before
    fun setUp() {
        dataSourceProvider.stub {
            on { get() }.doReturn(firstSource, secondSource)
        }
    }

    @Test
    fun `disposes inactive data source`() {
        val first = usecase.create()

        usecase.create()

        assertThat(first).isEqualTo(firstSource)
        verify(firstSource).dispose()
        verify(secondSource, never()).dispose()
    }

    @Test
    fun `sets proper refresh state`() {
        val first = usecase.create()

        usecase.refresh()

        verify(first).invalidate()
        verify(pagingEvents).onRefresh()
    }

    @Test
    fun `performs rety if source exists`() {
        var retryCalled = false
        firstSource.stub {
            on { retry } doReturn { retryCalled = true }
        }
        usecase.create()

        usecase.retry()

        assertThat(retryCalled).isTrue()
    }
}
