package pl.mkwiecinski.presentation.details.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.channels.sendBlocking
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.mapLatest
import pl.mkwiecinski.domain.details.GetRepositoryDetailsUseCase
import pl.mkwiecinski.presentation.base.BaseViewModel
import javax.inject.Inject

internal class DetailsViewModel @Inject constructor(
    val name: String,
    getRepositoryDetails: GetRepositoryDetailsUseCase
) : BaseViewModel() {

    val error = MutableLiveData<Throwable?>()
    private val loadChannel = ConflatedBroadcastChannel(Unit)
    val isLoading = MutableLiveData<Boolean>(false)

    val details = loadChannel.asFlow().mapLatest {
        isLoading.value = true
        val result = runCatching { getRepositoryDetails(name) }
            .onSuccess { error.value = null }
            .onFailure { error.value = it }
        isLoading.value = false
        result.getOrNull()
    }
        .asLiveData()

    fun retry() {
        loadChannel.sendBlocking(Unit)
    }
}
