package pl.mkwiecinski.domain.listing.persistences

import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject
import javax.inject.Singleton
import pl.mkwiecinski.domain.listing.models.LoadingState

interface PagingEventsPersistence {

    fun networkEvents(): Observable<LoadingState>
    fun refreshEvents(): Observable<LoadingState>

    fun onNetworkCall()
    fun onRefresh()
    fun onLoadSuccessful()
    fun onLoadError()
}

@Singleton
internal class InMemoryPagingEventsPersistence @Inject constructor() : PagingEventsPersistence {

    private val networkEvents = BehaviorSubject.create<LoadingState>()
    private val refreshEvents = BehaviorSubject.create<LoadingState>()

    override fun networkEvents() =
        networkEvents.observeOn(Schedulers.io())

    override fun refreshEvents() =
        refreshEvents.observeOn(Schedulers.io())

    override fun onNetworkCall() =
        networkEvents.onNext(LoadingState.RUNNING)

    override fun onRefresh() =
        refreshEvents.onNext(LoadingState.RUNNING)

    override fun onLoadSuccessful() {
        networkEvents.onNext(LoadingState.SUCCESS)
        refreshEvents.onNext(LoadingState.SUCCESS)
    }

    override fun onLoadError() {
        networkEvents.onNext(LoadingState.FAILED)
        refreshEvents.onNext(LoadingState.FAILED)
    }
}
