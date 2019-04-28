package pl.mkwiecinski.presentation.list.ui

import android.os.Bundle
import android.view.View
import androidx.lifecycle.observe
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
        val adapter = ReposAdapter(viewModel::retryFailed) {
            navController.navigate(ListFragmentDirections.actionListToDetails(it.name))
        }
        binding.repoList.adapter = adapter

        viewModel.repositories.observe(this, adapter::submitList)
        viewModel.networkState.observe(this) {
            adapter.networkState = it
        }
    }

    private fun setupSwipeRefresh() {
        binding.swipeRefresh.setOnRefreshListener {
            viewModel.refresh()
        }
        viewModel.refreshState.observe(this) {
            binding.swipeRefresh.isRefreshing = it == LoadingState.RUNNING
        }
    }
}
