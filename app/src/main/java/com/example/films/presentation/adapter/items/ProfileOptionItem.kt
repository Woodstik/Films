package com.example.films.presentation.adapter.items

import com.example.films.R
import com.example.films.data.models.ProfileOption

class ProfileOptionItem(val option: ProfileOption) : AdapterItem {

    override fun getViewType(): Int = R.layout.row_profile_option

    override fun isSameItem(otherItem: AdapterItem): Boolean {
        return isSameType(otherItem) && option == (otherItem as ProfileOptionItem).option
    }

    override fun isSameContent(otherItem: AdapterItem): Boolean {
        return option == (otherItem as ProfileOptionItem).option
    }
}
