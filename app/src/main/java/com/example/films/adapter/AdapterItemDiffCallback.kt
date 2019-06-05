package com.example.films.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.films.adapter.items.AdapterItem

class AdapterItemDiffCallback(
    private val oldAdapterItems: List<AdapterItem>,
    private val newAdapterItems: List<AdapterItem>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldAdapterItems.size

    override fun getNewListSize(): Int = newAdapterItems.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldAdapterItems[oldItemPosition].isSameItem(newAdapterItems[newItemPosition])
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldAdapterItems[oldItemPosition].isSameContent(newAdapterItems[newItemPosition])
    }
}
