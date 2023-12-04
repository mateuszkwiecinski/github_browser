package pl.mkwiecinski.presentation.list.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.paging.LoadState
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import pl.mkwiecinski.presentation.R
import pl.mkwiecinski.presentation.base.BaseFragment
import pl.mkwiecinski.presentation.databinding.FragmentListBinding
import pl.mkwiecinski.presentation.list.vm.ListViewModel

class ListFragment : BaseFragment<FragmentListBinding, ListViewModel>() {

    override val layoutId = R.layout.fragment_list
    override val viewModelClass = ListViewModel::class

    override fun init(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        FragmentListBinding.inflate(inflater, container, false).apply {
            val adapter = RepoAdapter(
                onItemSelected = { root.findNavController().navigate(R.id.details, bundleOf("name" to it.name)) },
            )
            val concatAdapter = adapter.withLoadStateFooter(ExampleLoadStateAdapter(adapter::retry))
            repoList.adapter = concatAdapter

            lifecycleScope.launch {
                viewModel.listPaging.collectLatest(adapter::submitData)
            }
            lifecycleScope.launch {
                adapter.loadStateFlow.collectLatest {
                    swipeRefresh.isRefreshing = when (it.refresh) {
                        is LoadState.NotLoading,
                        is LoadState.Error,
                        -> false

                        LoadState.Loading -> true
                    }
                }
            }
            swipeRefresh.setOnRefreshListener { adapter.refresh() }
            toolbar.setupWithNavController(root.findNavController())
        }
}
