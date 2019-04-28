package pl.mkwiecinski.presentation.details.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import pl.mkwiecinski.domain.details.entities.IssueInfo
import pl.mkwiecinski.presentation.databinding.ItemIssueInfoBinding
import pl.mkwiecinski.presentation.details.ui.IssueAdapter.IssueViewHolder

internal class IssueAdapter : ListAdapter<IssueInfo, IssueViewHolder>(IssueDiff) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        IssueViewHolder(ItemIssueInfoBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: IssueViewHolder, position: Int) {
        holder.binding.model = getItem(position)
    }

    class IssueViewHolder(val binding: ItemIssueInfoBinding) : RecyclerView.ViewHolder(binding.root)
}
