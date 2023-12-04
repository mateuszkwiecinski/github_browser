package pl.mkwiecinski.presentation.list.vm

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import pl.mkwiecinski.domain.listing.GetPagedRepositoriesUseCase
import pl.mkwiecinski.presentation.base.BaseViewModel
import javax.inject.Inject

class ListViewModel @Inject constructor(getPagedRepositories: GetPagedRepositoriesUseCase) : BaseViewModel() {

    val listPaging = getPagedRepositories()
        .cachedIn(viewModelScope)
}
