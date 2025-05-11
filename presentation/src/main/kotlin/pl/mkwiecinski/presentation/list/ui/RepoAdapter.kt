package pl.mkwiecinski.presentation.list.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import pl.mkwiecinski.domain.listing.entities.RepositoryInfo
import pl.mkwiecinski.domain.listing.models.LoadingState
import pl.mkwiecinski.presentation.databinding.ItemNetworkStateBinding
import pl.mkwiecinski.presentation.databinding.ItemRepoInfoBinding

internal class RepoAdapter(private val onItemSelected: (RepositoryInfo) -> Unit) :
    PagingDataAdapter<RepositoryInfo, RepoAdapter.RepoViewHolderViewHolder>(RepoDiff) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ItemRepoInfoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            .let(::RepoViewHolderViewHolder)

    override fun onBindViewHolder(holder: RepoViewHolderViewHolder, position: Int) {
        val item = getItem(position)
        holder.binding.txtName.text = item?.name
        holder.binding.txtUrl.text = item?.url
        holder.binding.root.setOnClickListener { item?.let(onItemSelected) }
    }

    class RepoViewHolderViewHolder(val binding: ItemRepoInfoBinding) : RecyclerView.ViewHolder(binding.root)
}

class LoadStateViewHolder(val binding: ItemNetworkStateBinding) : RecyclerView.ViewHolder(binding.root)

class ExampleLoadStateAdapter(private val onRetry: () -> Unit) : LoadStateAdapter<LoadStateViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState) =
        ItemNetworkStateBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            .let(::LoadStateViewHolder)

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {
        val loadingState = when (loadState) {
            is LoadState.NotLoading -> null
            LoadState.Loading -> LoadingState.RUNNING
            is LoadState.Error -> LoadingState.FAILED
        }
        holder.binding.pbProgress.isVisible = loadingState == LoadingState.RUNNING
        holder.binding.txtError.isVisible = loadingState == LoadingState.FAILED
        holder.binding.btnRetry.setOnClickListener { onRetry() }
    }
}
