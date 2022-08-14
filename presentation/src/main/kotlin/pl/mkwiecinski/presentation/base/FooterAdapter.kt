package pl.mkwiecinski.presentation.base

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

abstract class FooterAdapter<TFooter, TItem>(
    diffCallback: DiffUtil.ItemCallback<TItem>,
) : ListAdapter<TItem, RecyclerView.ViewHolder>(diffCallback) {

    var footerData: TFooter? = null
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
                notifyItemChanged(itemCount - FOOTER)
            }
        }
    val actualItemsCount
        get() = super.getItemCount()

    protected abstract fun hasExtraRow(): Boolean

    override fun getItemCount() =
        super.getItemCount() + if (hasExtraRow()) FOOTER else 0

    override fun getItemViewType(position: Int) =
        if (hasExtraRow() && position == itemCount - FOOTER) {
            FOOTER_TYPE
        } else {
            ITEM_TYPE
        }

    companion object {
        const val FOOTER_TYPE = 420
        const val ITEM_TYPE = 562
        const val FOOTER = 1
    }
}
