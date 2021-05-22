package pl.mkwiecinski.presentation.details.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.onStart
import pl.mkwiecinski.domain.details.GetRepositoryDetailsUseCase
import pl.mkwiecinski.presentation.base.BaseViewModel
import javax.inject.Inject

internal class DetailsViewModel @Inject constructor(
    val name: String,
    getRepositoryDetails: GetRepositoryDetailsUseCase
) : BaseViewModel() {

    val error = MutableLiveData<Throwable?>()
    private val loadRequests = MutableSharedFlow<Unit>(extraBufferCapacity = 1)
    val isLoading = MutableLiveData(false)

    val details = loadRequests
        .onStart { emit(Unit) }
        .mapLatest {
            isLoading.value = true
            val result = runCatching { getRepositoryDetails(name) }
                .onSuccess { error.value = null }
                .onFailure { error.value = it }
            isLoading.value = false
            result.getOrNull()
        }
        .asLiveData()

    fun retry() {
        loadRequests.tryEmit(Unit)
    }
}
