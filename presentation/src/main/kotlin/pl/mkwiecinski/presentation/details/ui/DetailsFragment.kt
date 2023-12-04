package pl.mkwiecinski.presentation.details.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.map
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import pl.mkwiecinski.presentation.R
import pl.mkwiecinski.presentation.base.BaseFragment
import pl.mkwiecinski.presentation.databinding.FragmentDetailsBinding
import pl.mkwiecinski.presentation.details.vm.DetailsViewModel

class DetailsFragment : BaseFragment<FragmentDetailsBinding, DetailsViewModel>() {

    override val layoutId = R.layout.fragment_details
    override val viewModelClass = DetailsViewModel::class

    override fun init(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        FragmentDetailsBinding.inflate(inflater, container, false).apply {
            bindIssues()
            bindPullRequests()
            bindFab()
            toolbar.setupWithNavController(root.findNavController())

            viewModel.error.observe(viewLifecycleOwner) {
                errorContainer.isVisible = it != null
                scrollView.isVisible = it == null
            }
            viewModel.isLoading.observe(viewLifecycleOwner) {
                btnRetry.isEnabled = it != true
                pbProgress.isVisible = it == true
            }
            btnRetry.setOnClickListener { viewModel.retry() }
            viewModel.details.observe(viewLifecycleOwner) {
                txtName.text = it?.name
                txtUrl.text = it?.url
                txtUrl.isVisible = it?.url != null
                txtOpenedIssuesLabel.isVisible = it != null
                txtOpenedIssuesLabel.text = resources.getString(R.string.opened_issues_count, it?.issues?.openedTotalCount ?: 0)
                txtClosedIssuesLabel.isVisible = it != null
                txtClosedIssuesLabel.text = resources.getString(R.string.closed_issues_count, it?.issues?.closedTotalCount ?: 0)
                txtOpenedPrsLabel.isVisible = it != null
                txtOpenedPrsLabel.text = resources.getString(R.string.opened_pull_requests_count, it?.pullRequests?.openedTotalCount ?: 0)
                txtClosedPrsLabel.isVisible = it != null
                txtClosedPrsLabel.text = resources.getString(R.string.closed_pull_requests_count, it?.pullRequests?.closedTotalCount ?: 0)
            }
        }

    private fun FragmentDetailsBinding.bindFab() {
        viewModel.details
            .map { it?.url }
            .observe(viewLifecycleOwner) { url ->
                val onClick = url?.let {
                    View.OnClickListener {
                        val intent = Intent(Intent.ACTION_VIEW).apply {
                            data = Uri.parse(url)
                        }
                        startActivity(intent)
                    }
                }
                fabOpenBrowser.setOnClickListener(onClick)
                fabOpenBrowser.isVisible = url != null
            }
    }

    private fun FragmentDetailsBinding.bindPullRequests() {
        val openedPRsAdapter = PullRequestAdapter()
        listOpenedPRs.adapter = openedPRsAdapter
        viewModel.details
            .map { it?.pullRequests }
            .observe(viewLifecycleOwner) {
                openedPRsAdapter.submitList(it?.openedPreview)
                openedPRsAdapter.footerData = it?.openedTotalCount
            }

        val closedPRsAdapter = PullRequestAdapter()
        listClosedPRs.adapter = closedPRsAdapter
        viewModel.details
            .map { it?.pullRequests }
            .observe(viewLifecycleOwner) {
                closedPRsAdapter.submitList(it?.closedPreview)
                closedPRsAdapter.footerData = it?.closedTotalCount
            }
    }

    private fun FragmentDetailsBinding.bindIssues() {
        val openedIssuesAdapter = IssueAdapter()
        listOpenedIssues.adapter = openedIssuesAdapter
        viewModel.details
            .map { it?.issues }
            .observe(viewLifecycleOwner) {
                openedIssuesAdapter.submitList(it?.openedPreview)
                openedIssuesAdapter.footerData = it?.openedTotalCount
            }

        val closedIssuesAdapter = IssueAdapter()
        listClosedIssues.adapter = closedIssuesAdapter
        viewModel.details
            .map { it?.issues }
            .observe(viewLifecycleOwner) {
                closedIssuesAdapter.submitList(it?.closedPreview)
                closedIssuesAdapter.footerData = it?.closedTotalCount
            }
    }
}
