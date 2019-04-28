package pl.mkwiecinski.presentation.details.vm

import pl.mkwiecinski.domain.details.GetRepositoryDetailsUseCase
import pl.mkwiecinski.presentation.base.BaseViewModel
import javax.inject.Inject

internal class DetailsViewModel @Inject constructor(
    val name: String,
    getRepositoryDetails: GetRepositoryDetailsUseCase
) : BaseViewModel() {

    val details = getRepositoryDetails(name).toLiveData()
}
