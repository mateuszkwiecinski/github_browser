package pl.mkwiecinski.presentation.list.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import pl.mkwiecinski.domain.listing.entities.RepositoryInfo
import pl.mkwiecinski.domain.listing.models.LoadingState
import pl.mkwiecinski.presentation.R
import pl.mkwiecinski.presentation.databinding.ItemNetworkStateBinding
import pl.mkwiecinski.presentation.databinding.ItemRepoInfoBinding

internal class RepoAdapter(
    private val onRetry: () -> Unit,
    private val onItemSelected: (RepositoryInfo) -> Unit
) : PagedListAdapter<RepositoryInfo, RepoAdapter.BindableViewHolder>(RepoDiff) {

    var networkState: LoadingState? = null
        set(value) {
            val previousState = field
            val hadExtraRow = hasExtraRow()
            field = value
            val hasExtraRow = hasExtraRow()
            if (hadExtraRow != hasExtraRow) {
                if (hadExtraRow) {
                    notifyItemRemoved(super.getItemCount())
                } else {
                    notifyItemInserted(super.getItemCount())
                }
            } else if (hasExtraRow && previousState != value) {
                notifyItemChanged(itemCount - 1)
            }
        }

    override fun getItemViewType(position: Int) =
        if (hasExtraRow() && position == itemCount - 1) {
            R.layout.item_network_state
        } else {
            R.layout.item_repo_info
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindableViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            R.layout.item_repo_info ->
                ItemRepoInfoBinding.inflate(inflater, parent, false).let(BindableViewHolder::Repo)
            R.layout.item_network_state ->
                ItemNetworkStateBinding.inflate(inflater, parent, false).let(BindableViewHolder::NetworkState)
            else -> error("Unsupported type")
        }
    }

    override fun onBindViewHolder(holder: BindableViewHolder, position: Int) {
        when (holder) {
            is BindableViewHolder.Repo -> {
                holder.binding.model = getItem(position)
                holder.binding.root.setOnClickListener {
                    getItem(holder.adapterPosition)?.let(onItemSelected)
                }
            }
            is BindableViewHolder.NetworkState -> {
                holder.binding.model = networkState
                holder.binding.btnRetry.setOnClickListener {
                    onRetry()
                }
            }
        }
        holder.binding.executePendingBindings()
    }

    override fun getItemCount(): Int {
        return super.getItemCount() + if (hasExtraRow()) 1 else 0
    }

    private fun hasExtraRow() = networkState != null && networkState != LoadingState.SUCCESS

    sealed class BindableViewHolder(open val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {
        data class Repo(override val binding: ItemRepoInfoBinding) : BindableViewHolder(binding)
        data class NetworkState(override val binding: ItemNetworkStateBinding) : BindableViewHolder(binding)
    }
}
