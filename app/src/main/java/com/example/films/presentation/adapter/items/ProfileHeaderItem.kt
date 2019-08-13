package com.example.films.presentation.adapter.items

import com.example.films.R
import com.example.films.data.models.Profile

class ProfileHeaderItem(val profile: Profile) : AdapterItem {

    override fun getViewType(): Int = R.layout.row_profile_header

    override fun isSameItem(otherItem: AdapterItem): Boolean {
        return isSameType(otherItem)
    }

    override fun isSameContent(otherItem: AdapterItem): Boolean {
        return profile == (otherItem as ProfileHeaderItem).profile
    }
}
