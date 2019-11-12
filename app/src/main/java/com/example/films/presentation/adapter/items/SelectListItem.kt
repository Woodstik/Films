package com.example.films.presentation.adapter.items

import com.example.films.R
import com.example.films.data.models.MovieList

class SelectListItem(val movieList: MovieList) : AdapterItem {

    override fun getViewType(): Int = R.layout.row_select_list

    override fun isSameItem(otherItem: AdapterItem): Boolean {
        return isSameType(otherItem) && movieList.id == (otherItem as SelectListItem).movieList.id
    }

    override fun isSameContent(otherItem: AdapterItem): Boolean {
        return isSameType(otherItem) && movieList == (otherItem as SelectListItem).movieList
    }
}