package com.example.films.presentation.adapter.items

import androidx.annotation.StringRes
import com.example.films.R

class HomeLabelItem(@StringRes val label: Int) : AdapterItem {

    override fun getViewType(): Int = R.layout.row_home_label

    override fun getSpan(): Int = R.integer.home_max_span

    override fun isSameItem(otherItem: AdapterItem): Boolean {
        return isSameType(otherItem)
    }

    override fun isSameContent(otherItem: AdapterItem): Boolean {
        return isSameType(otherItem) && label == (otherItem as HomeLabelItem).label
    }
}
