package pl.mkwiecinski.presentation.list.ui

import androidx.recyclerview.widget.DiffUtil
import pl.mkwiecinski.domain.entities.RepositoryInfo

object RepoDiff : DiffUtil.ItemCallback<RepositoryInfo>() {

    override fun areItemsTheSame(oldItem: RepositoryInfo, newItem: RepositoryInfo) =
        oldItem.name == newItem.name

    override fun areContentsTheSame(oldItem: RepositoryInfo, newItem: RepositoryInfo) =
        oldItem == newItem
}
