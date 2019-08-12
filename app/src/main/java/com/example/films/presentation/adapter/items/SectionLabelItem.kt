package com.example.films.presentation.adapter.items

import androidx.annotation.StringRes
import com.example.films.R

class SectionLabelItem(@StringRes val label: Int) : AdapterItem {

    override fun getViewType(): Int = R.layout.row_section_label

    override fun isSameItem(otherItem: AdapterItem): Boolean {
        return isSameType(otherItem)
    }

    override fun isSameContent(otherItem: AdapterItem): Boolean {
        return isSameType(otherItem) && label == (otherItem as SectionLabelItem).label
    }
}
