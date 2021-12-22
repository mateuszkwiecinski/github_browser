package pl.mkwiecinski.presentation.details.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import pl.mkwiecinski.domain.details.GetRepositoryDetailsUseCase
import pl.mkwiecinski.domain.details.LoadDetailsUseCase
import pl.mkwiecinski.presentation.base.BaseViewModel
import javax.inject.Inject

internal class DetailsViewModel @Inject constructor(
    getRepositoryDetails: GetRepositoryDetailsUseCase,
    private val refresh: LoadDetailsUseCase,
) : BaseViewModel() {

    val error = MutableLiveData<Throwable?>()
    val isLoading = MutableLiveData(false)
    val details = getRepositoryDetails().asLiveData()

    init {
        retry()
    }

    fun retry() {
        viewModelScope.launch {
            isLoading.value = true
            runCatching { refresh() }
                .onSuccess { error.value = null }
                .onFailure { error.value = it }
            isLoading.value = false
        }
    }
}
