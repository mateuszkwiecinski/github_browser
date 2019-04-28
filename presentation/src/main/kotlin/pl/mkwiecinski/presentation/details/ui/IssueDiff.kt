package pl.mkwiecinski.presentation.details.ui

import androidx.recyclerview.widget.DiffUtil
import pl.mkwiecinski.domain.details.entities.IssueInfo

object IssueDiff : DiffUtil.ItemCallback<IssueInfo>() {
    override fun areItemsTheSame(oldItem: IssueInfo, newItem: IssueInfo) = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: IssueInfo, newItem: IssueInfo) = oldItem == newItem
}
