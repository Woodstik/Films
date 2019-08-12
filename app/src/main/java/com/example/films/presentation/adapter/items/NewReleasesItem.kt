package com.example.films.presentation.adapter.items

import com.example.films.R
import com.example.films.data.models.Movie

class NewReleasesItem(val newReleases: List<Movie>) : AdapterItem {

    override fun getViewType(): Int = R.layout.row_new_releases

    override fun isSameItem(otherItem: AdapterItem): Boolean {
        return isSameType(otherItem)
    }

    override fun isSameContent(otherItem: AdapterItem): Boolean {
        return newReleases == (otherItem as NewReleasesItem).newReleases
    }
}
