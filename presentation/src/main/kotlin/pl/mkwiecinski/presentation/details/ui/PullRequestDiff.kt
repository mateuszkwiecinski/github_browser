package pl.mkwiecinski.presentation.details.ui

import androidx.recyclerview.widget.DiffUtil
import pl.mkwiecinski.domain.details.entities.PullRequestPreview

object PullRequestDiff : DiffUtil.ItemCallback<PullRequestPreview>() {

    override fun areItemsTheSame(oldItem: PullRequestPreview, newItem: PullRequestPreview) = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: PullRequestPreview, newItem: PullRequestPreview) = oldItem == newItem
}
