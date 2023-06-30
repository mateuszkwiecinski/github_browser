package pl.mkwiecinski.presentation.list.ui

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.lifecycle.lifecycleScope
import androidx.navigation.ui.setupWithNavController
import androidx.paging.LoadState
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import pl.mkwiecinski.presentation.R
import pl.mkwiecinski.presentation.base.BaseFragment
import pl.mkwiecinski.presentation.databinding.FragmentListBinding
import pl.mkwiecinski.presentation.list.vm.ListViewModel

internal class ListFragment : BaseFragment<FragmentListBinding, ListViewModel>() {

    override val layoutId = R.layout.fragment_list
    override val viewModelClass = ListViewModel::class

    override fun init(savedInstanceState: Bundle?) {
        val adapter = RepoAdapter(
            onItemSelected = { navController.navigate(R.id.details, bundleOf("name" to it.name)) },
        )
        val concatAdapter = adapter.withLoadStateFooter(ExampleLoadStateAdapter(adapter::retry))
        binding.repoList.adapter = concatAdapter

        lifecycleScope.launch {
            viewModel.listPaging.collectLatest(adapter::submitData)
        }
        lifecycleScope.launch {
            adapter.loadStateFlow.collectLatest {
                binding.swipeRefresh.isRefreshing = when (it.refresh) {
                    is LoadState.NotLoading,
                    is LoadState.Error,
                    -> false

                    LoadState.Loading -> true
                }
            }
        }
        binding.swipeRefresh.setOnRefreshListener { adapter.refresh() }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar.setupWithNavController(navController)
    }
}
