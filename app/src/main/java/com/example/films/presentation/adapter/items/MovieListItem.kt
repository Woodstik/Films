package com.example.films.presentation.adapter.items

import com.example.films.R
import com.example.films.data.models.MovieList

class MovieListItem(val movieList: MovieList) : AdapterItem {

    override fun getViewType(): Int = R.layout.row_user_list

    override fun isSameItem(otherItem: AdapterItem): Boolean {
        return isSameType(otherItem) && movieList.id == (otherItem as MovieListItem).movieList.id
    }

    override fun isSameContent(otherItem: AdapterItem): Boolean {
        return movieList == (otherItem as MovieListItem).movieList
    }
}
