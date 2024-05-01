package pl.mkwiecinski.presentation.details.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import pl.mkwiecinski.domain.details.entities.PullRequestPreview
import pl.mkwiecinski.presentation.R
import pl.mkwiecinski.presentation.base.FooterAdapter
import pl.mkwiecinski.presentation.databinding.ItemDetailFooterBinding
import pl.mkwiecinski.presentation.databinding.ItemPullRequestInfoBinding

internal class PullRequestAdapter : FooterAdapter<Int, PullRequestPreview>(PullRequestDiff) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            ITEM_TYPE -> PullRequestViewHolder(
                ItemPullRequestInfoBinding.inflate(inflater, parent, false),
            )

            FOOTER_TYPE -> FooterViewHolder(
                ItemDetailFooterBinding.inflate(inflater, parent, false),
            )

            else -> error("Unsupported type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val context = holder.itemView.context
        when (holder) {
            is PullRequestViewHolder -> {
                val item = getItem(position)
                holder.binding.txtNumber.text = context.getString(R.string.issue_number_format, item.number)
                holder.binding.txtName.text = item.name
                holder.binding.txtUrl.text = item.url
            }

            is FooterViewHolder -> {
                holder.binding.root.text = footerData?.minus(actualItemsCount)
                    ?.let { context.getString(R.string.details_preview_footer, it) }
            }
        }
    }

    override fun hasExtraRow() = footerData != null && footerData != actualItemsCount

    class PullRequestViewHolder(val binding: ItemPullRequestInfoBinding) : RecyclerView.ViewHolder(binding.root)
    class FooterViewHolder(val binding: ItemDetailFooterBinding) : RecyclerView.ViewHolder(binding.root)
}
