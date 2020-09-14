package pl.mkwiecinski.presentation.list.ui

import android.os.Bundle
import android.view.View
import androidx.navigation.ui.setupWithNavController
import pl.mkwiecinski.domain.listing.models.LoadingState
import pl.mkwiecinski.presentation.R
import pl.mkwiecinski.presentation.base.BaseFragment
import pl.mkwiecinski.presentation.databinding.FragmentListBinding
import pl.mkwiecinski.presentation.list.vm.ListViewModel

internal class ListFragment : BaseFragment<FragmentListBinding, ListViewModel>() {

    override val layoutId = R.layout.fragment_list
    override val viewModelClass = ListViewModel::class

    override fun init(savedInstanceState: Bundle?) {
        setupList()
        setupSwipeRefresh()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar.setupWithNavController(navController)
    }

    private fun setupList() {
        val adapter = RepoAdapter(
            onRetry = viewModel::retryFailed,
            onItemSelected = { navController.navigate(ListFragmentDirections.actionListToDetails(it.name)) },
            onItemLongClicked = { navController.navigate(ListFragmentDirections.actionListToKeyboard()) }
        )
        binding.repoList.adapter = adapter

        viewModel.repositories.observe(viewLifecycleOwner, adapter::submitList)
        viewModel.networkState.observe(viewLifecycleOwner) { adapter.networkState = it }
    }

    private fun setupSwipeRefresh() {
        viewModel.refreshState.observe(viewLifecycleOwner) {
            binding.swipeRefresh.isRefreshing = it == LoadingState.RUNNING
        }
    }
}
