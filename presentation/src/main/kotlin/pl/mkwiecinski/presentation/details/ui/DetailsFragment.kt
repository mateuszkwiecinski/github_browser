package pl.mkwiecinski.presentation.details.ui

import android.os.Bundle
import android.view.View
import androidx.lifecycle.observe
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.setupWithNavController
import pl.mkwiecinski.presentation.R
import pl.mkwiecinski.presentation.base.BaseFragment
import pl.mkwiecinski.presentation.databinding.FragmentDetailsBinding
import pl.mkwiecinski.presentation.details.vm.DetailsViewModel

internal class DetailsFragment : BaseFragment<FragmentDetailsBinding, DetailsViewModel>() {

    override val layoutId = R.layout.fragment_details
    override val viewModelClass = DetailsViewModel::class

    internal val args by navArgs<DetailsFragmentArgs>()

    override fun init(savedInstanceState: Bundle?) {
        bindIssues()
        bindPullRequests()
    }

    private fun bindPullRequests() {
        val openedPRsAdapter = PullRequestAdapter()
        binding.listOpenedPRs.adapter = openedPRsAdapter
        viewModel.details.observe(this) {
            openedPRsAdapter.submitList(it.openedPullRequests.preview)
        }

        val closedPRsAdapter = PullRequestAdapter()
        binding.listClosedPRs.adapter = closedPRsAdapter
        viewModel.details.observe(this) {
            closedPRsAdapter.submitList(it.closedPullRequests.preview)
        }
    }

    private fun bindIssues() {
        val openedIssuesAdapter = IssueAdapter()
        binding.listOpenedIssues.adapter = openedIssuesAdapter
        viewModel.details.observe(this) {
            openedIssuesAdapter.submitList(it.openedIssues.preview)
        }

        val closedIssuesAdapter = IssueAdapter()
        binding.listClosedIssues.adapter = closedIssuesAdapter
        viewModel.details.observe(this) {
            closedIssuesAdapter.submitList(it.closedIssues.preview)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.toolbar.setupWithNavController(navController)
    }
}
