package pl.mkwiecinski.presentation.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import pl.mkwiecinski.domain.base.plusAssign

internal abstract class BaseViewModel : ViewModel() {

    protected val disposeBag = CompositeDisposable()

    override fun onCleared() {
        super.onCleared()
        disposeBag.clear()
    }

    protected fun <T : Any> Observable<T>.toLiveData(): LiveData<T?> {
        val liveData = MutableLiveData<T>()

        disposeBag += observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(onNext = { liveData.value = it })

        return liveData
    }
}
