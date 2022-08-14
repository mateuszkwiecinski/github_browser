package pl.mkwiecinski.presentation.details.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import pl.mkwiecinski.domain.details.entities.IssuePreview
import pl.mkwiecinski.presentation.base.FooterAdapter
import pl.mkwiecinski.presentation.databinding.ItemDetailFooterBinding
import pl.mkwiecinski.presentation.databinding.ItemIssueInfoBinding

internal class IssueAdapter : FooterAdapter<Int, IssuePreview>(IssueDiff) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            ITEM_TYPE -> IssueViewHolder(
                ItemIssueInfoBinding.inflate(inflater, parent, false),
            )
            FOOTER_TYPE -> FooterViewHolder(
                ItemDetailFooterBinding.inflate(inflater, parent, false),
            )
            else -> error("Unsupported type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is IssueViewHolder -> holder.binding.model = getItem(position)
            is FooterViewHolder -> holder.binding.count = footerData?.minus(actualItemsCount)
        }
    }

    override fun hasExtraRow() = footerData != null && footerData != actualItemsCount

    class IssueViewHolder(val binding: ItemIssueInfoBinding) : RecyclerView.ViewHolder(binding.root)
    class FooterViewHolder(val binding: ItemDetailFooterBinding) : RecyclerView.ViewHolder(binding.root)
}
