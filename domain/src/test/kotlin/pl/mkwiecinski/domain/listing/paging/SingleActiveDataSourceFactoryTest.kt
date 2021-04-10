package pl.mkwiecinski.domain.listing.paging

import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.never
import com.nhaarman.mockitokotlin2.stub
import com.nhaarman.mockitokotlin2.verify
import javax.inject.Provider
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import pl.mkwiecinski.domain.listing.persistences.InMemoryPagingEventsPersistence

@RunWith(MockitoJUnitRunner::class)
internal class SingleActiveDataSourceFactoryTest {

    @Mock
    private lateinit var pagingEvents: InMemoryPagingEventsPersistence

    @Mock
    private lateinit var dataSourceProvider: Provider<RepoDataSource>

    @Mock
    private lateinit var firstSource: RepoDataSource

    @Mock
    private lateinit var secondSource: RepoDataSource

    @Mock
    private lateinit var pagedListBuilder: PagedListBuilder

    @InjectMocks
    private lateinit var factory: SingleActivePagingSourceFactory

    @Before
    fun setUp() {
        dataSourceProvider.stub {
            on { get() }.doReturn(firstSource, secondSource)
        }
    }

    @Test
    fun `disposes inactive data source`() {
        val first = factory.create()

        factory.create()

        assertThat(first).isEqualTo(firstSource)
        verify(firstSource).close()
        verify(secondSource, never()).close()
    }

    @Test
    fun `sets proper refresh state`() {
        val first = factory.create()

        factory.refresh()

        verify(first).invalidate()
        verify(pagingEvents).onRefresh()
    }

    @Test
    fun `performs retry if source exists`() {
        var retryCalled = false
        firstSource.stub {
            on { retry } doReturn { retryCalled = true }
        }
        factory.create()

        factory.retry()

        assertThat(retryCalled).isTrue()
    }
}
