package com.example.films.adapter.items

import androidx.annotation.IntegerRes
import androidx.annotation.LayoutRes
import com.example.films.R

interface AdapterItem {

    @LayoutRes
    fun getViewType(): Int

    @IntegerRes
    fun getSpan(): Int {
        return R.integer.default_span
    }

    fun isSameItem(otherItem: AdapterItem): Boolean {
        return false
    }

    fun isSameContent(otherItem: AdapterItem): Boolean {
        return false
    }

    fun isSameType(otherItem: AdapterItem): Boolean {
        return getViewType() == otherItem.getViewType()
    }
}
