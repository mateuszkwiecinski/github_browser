package pl.mkwiecinski.presentation.details.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.lifecycle.map
import androidx.navigation.ui.setupWithNavController
import pl.mkwiecinski.presentation.R
import pl.mkwiecinski.presentation.base.BaseFragment
import pl.mkwiecinski.presentation.databinding.FragmentDetailsBinding
import pl.mkwiecinski.presentation.details.vm.DetailsViewModel

internal class DetailsFragment : BaseFragment<FragmentDetailsBinding, DetailsViewModel>() {

    override val layoutId = R.layout.fragment_details
    override val viewModelClass = DetailsViewModel::class

    override fun init(savedInstanceState: Bundle?) {
        bindIssues()
        bindPullRequests()
        bindFab()
    }

    private fun bindFab() {
        viewModel.details
            .map { it?.url }
            .observe(this) { url ->
                val onClick = url?.let {
                    View.OnClickListener {
                        val intent = Intent(Intent.ACTION_VIEW).apply {
                            data = Uri.parse(url)
                        }
                        startActivity(intent)
                    }
                }
                binding.fabOpenBrowser.setOnClickListener(onClick)
            }
    }

    private fun bindPullRequests() {
        val openedPRsAdapter = PullRequestAdapter()
        binding.listOpenedPRs.adapter = openedPRsAdapter
        viewModel.details
            .map { it?.pullRequests }
            .observe(this) {
                openedPRsAdapter.submitList(it?.openedPreview)
                openedPRsAdapter.footerData = it?.openedTotalCount
            }

        val closedPRsAdapter = PullRequestAdapter()
        binding.listClosedPRs.adapter = closedPRsAdapter
        viewModel.details
            .map { it?.pullRequests }
            .observe(this) {
                closedPRsAdapter.submitList(it?.closedPreview)
                closedPRsAdapter.footerData = it?.closedTotalCount
            }
    }

    private fun bindIssues() {
        val openedIssuesAdapter = IssueAdapter()
        binding.listOpenedIssues.adapter = openedIssuesAdapter
        viewModel.details
            .map { it?.issues }
            .observe(this) {
                openedIssuesAdapter.submitList(it?.openedPreview)
                openedIssuesAdapter.footerData = it?.openedTotalCount
            }

        val closedIssuesAdapter = IssueAdapter()
        binding.listClosedIssues.adapter = closedIssuesAdapter
        viewModel.details
            .map { it?.issues }
            .observe(this) {
                closedIssuesAdapter.submitList(it?.closedPreview)
                closedIssuesAdapter.footerData = it?.closedTotalCount
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.toolbar.setupWithNavController(navController)
    }
}
