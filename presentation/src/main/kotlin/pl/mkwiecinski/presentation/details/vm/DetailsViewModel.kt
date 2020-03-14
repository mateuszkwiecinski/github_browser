package pl.mkwiecinski.presentation.details.vm

import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject
import pl.mkwiecinski.domain.details.GetRepositoryDetails
import pl.mkwiecinski.presentation.base.BaseViewModel

internal class DetailsViewModel @Inject constructor(
    val name: String,
    getRepositoryDetails: GetRepositoryDetails
) : BaseViewModel() {

    val error = MutableLiveData<Throwable?>()
    private val loadSubject = BehaviorSubject.createDefault(Unit)
    val isLoading = MutableLiveData<Boolean>(false)

    val details =
        loadSubject.switchMapMaybe {
            getRepositoryDetails(name)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { isLoading.value = true }
                .doFinally { isLoading.value = false }
                .doOnError { error.value = it }
                .doOnSuccess { error.value = null }
                .toMaybe()
                .onErrorComplete()
        }
            .toLiveData()

    fun retry() {
        loadSubject.onNext(Unit)
    }
}
