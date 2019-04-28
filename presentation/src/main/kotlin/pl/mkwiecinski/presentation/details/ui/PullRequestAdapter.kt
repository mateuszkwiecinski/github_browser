package pl.mkwiecinski.presentation.details.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import pl.mkwiecinski.domain.details.entities.PullRequestInfo
import pl.mkwiecinski.presentation.databinding.ItemPullRequestInfoBinding
import pl.mkwiecinski.presentation.details.ui.PullRequestAdapter.PullRequestViewHolder

internal class PullRequestAdapter : ListAdapter<PullRequestInfo, PullRequestViewHolder>(PullRequestDiff) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ItemPullRequestInfoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            .let(::PullRequestViewHolder)

    override fun onBindViewHolder(holder: PullRequestViewHolder, position: Int) {
        holder.binding.model = getItem(position)
    }

    class PullRequestViewHolder(val binding: ItemPullRequestInfoBinding) : RecyclerView.ViewHolder(binding.root)
}
