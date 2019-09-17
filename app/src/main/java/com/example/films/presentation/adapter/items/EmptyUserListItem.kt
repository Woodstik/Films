package com.example.films.presentation.adapter.items

import com.example.films.R

class EmptyUserListItem : AdapterItem {

    override fun getViewType(): Int = R.layout.row_empty_user_list

    override fun isSameItem(otherItem: AdapterItem): Boolean {
        return isSameType(otherItem)
    }
}