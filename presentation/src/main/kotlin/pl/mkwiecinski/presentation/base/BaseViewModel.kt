package pl.mkwiecinski.presentation.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

internal abstract class BaseViewModel : ViewModel() {

    protected val disposeBag = CompositeDisposable()

    override fun onCleared() {
        super.onCleared()
        disposeBag.clear()
    }

    protected fun Disposable.disposeInBag() {
        disposeBag.add(this)
    }

    protected fun <T> Observable<T>.toLiveData(): LiveData<T?> {
        val liveData = MutableLiveData<T>()

        observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                liveData.value = it
            }
            .disposeInBag()

        return liveData
    }

    protected fun <T> Single<T>.toLiveData() =
        toObservable().toLiveData()
}
