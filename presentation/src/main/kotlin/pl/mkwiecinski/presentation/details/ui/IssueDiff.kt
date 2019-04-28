package pl.mkwiecinski.presentation.details.ui

import androidx.recyclerview.widget.DiffUtil
import pl.mkwiecinski.domain.details.entities.IssuePreview

object IssueDiff : DiffUtil.ItemCallback<IssuePreview>() {
    override fun areItemsTheSame(oldItem: IssuePreview, newItem: IssuePreview) = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: IssuePreview, newItem: IssuePreview) = oldItem == newItem
}
