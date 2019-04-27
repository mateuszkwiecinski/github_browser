package pl.mkwiecinski.domain.paging

import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import pl.mkwiecinski.domain.paging.models.LoadingState
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class InMemoryPagingEvents @Inject constructor() {

    private val networkEvents = BehaviorSubject.create<LoadingState>()
    private val refreshEvents = BehaviorSubject.create<LoadingState>()

    fun networkEvents() =
        networkEvents.observeOn(Schedulers.io())

    fun refreshEvents() =
        refreshEvents.observeOn(Schedulers.io())

    fun onNetworkCall() =
        networkEvents.onNext(LoadingState.RUNNING)

    fun onRefresh() =
        refreshEvents.onNext(LoadingState.RUNNING)

    fun onLoadSuccessful() {
        networkEvents.onNext(LoadingState.SUCCESS)
        refreshEvents.onNext(LoadingState.SUCCESS)
    }

    fun onLoadError() {
        networkEvents.onNext(LoadingState.FAILED)
        refreshEvents.onNext(LoadingState.FAILED)
    }
}
