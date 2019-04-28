package pl.mkwiecinski.presentation.details.ui

import androidx.recyclerview.widget.DiffUtil
import pl.mkwiecinski.domain.details.entities.PullRequestInfo

object PullRequestDiff : DiffUtil.ItemCallback<PullRequestInfo>() {
    override fun areItemsTheSame(oldItem: PullRequestInfo, newItem: PullRequestInfo) = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: PullRequestInfo, newItem: PullRequestInfo) = oldItem == newItem
}
