package pl.mkwiecinski.presentation.list.ui

import androidx.recyclerview.widget.DiffUtil
import pl.mkwiecinski.domain.listing.entities.RepositoryInfo

object RepoDiff : DiffUtil.ItemCallback<RepositoryInfo>() {

    override fun areItemsTheSame(oldItem: RepositoryInfo, newItem: RepositoryInfo) =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: RepositoryInfo, newItem: RepositoryInfo) =
        oldItem == newItem
}
