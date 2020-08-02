package pl.mkwiecinski.domain.listing.persistences

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import pl.mkwiecinski.domain.listing.models.LoadingState
import javax.inject.Inject
import javax.inject.Singleton

interface PagingEventsPersistence {

    fun networkEvents(): Flow<LoadingState>
    fun refreshEvents(): Flow<LoadingState>

    fun onNetworkCall()
    fun onRefresh()
    fun onLoadSuccessful()
    fun onLoadError()
}

@Singleton
@OptIn(ExperimentalCoroutinesApi::class)
internal class InMemoryPagingEventsPersistence @Inject constructor() : PagingEventsPersistence {

    private val networkEvents = MutableStateFlow(LoadingState.SUCCESS)
    private val refreshEvents = MutableStateFlow(LoadingState.SUCCESS)

    override fun networkEvents(): StateFlow<LoadingState> =
        networkEvents

    override fun refreshEvents(): StateFlow<LoadingState> =
        refreshEvents

    override fun onNetworkCall() {
        networkEvents.value = LoadingState.RUNNING
    }

    override fun onRefresh() {
        refreshEvents.value = LoadingState.RUNNING
    }

    override fun onLoadSuccessful() {
        networkEvents.value = LoadingState.SUCCESS
        refreshEvents.value = LoadingState.SUCCESS
    }

    override fun onLoadError() {
        networkEvents.value = LoadingState.FAILED
        refreshEvents.value = LoadingState.FAILED
    }
}
