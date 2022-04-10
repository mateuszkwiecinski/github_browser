package pl.mkwiecinski.presentation.details.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import pl.mkwiecinski.domain.details.GetRepositoryDetailsUseCase
import pl.mkwiecinski.domain.details.HelloHello
import pl.mkwiecinski.presentation.base.BaseViewModel
import javax.inject.Inject

internal class DetailsViewModel @Inject constructor(
    private val refresh: HelloHello,
    getRepositoryDetails: GetRepositoryDetailsUseCase,
) : BaseViewModel() {

    val error = MutableLiveData<Throwable?>()
    val details = getRepositoryDetails().asLiveData()
    val isLoading = MutableLiveData(false)

    init {
        retry()
    }

    fun retry() {
        viewModelScope.launch {
            isLoading.value = true
            error.value = runCatching { refresh() }.exceptionOrNull()
            isLoading.value = false
        }
    }
}
