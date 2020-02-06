package com.example.films.presentation.adapter.items

import com.example.films.R
import com.example.films.data.models.MovieList

class MovieListHeaderItem(val movieList: MovieList)  : AdapterItem {

    override fun getViewType(): Int = R.layout.row_movie_list_header

    override fun isSameItem(otherItem: AdapterItem): Boolean {
        return isSameType(otherItem) && movieList.id == (otherItem as MovieListHeaderItem).movieList.id
    }

    override fun isSameContent(otherItem: AdapterItem): Boolean {
        return movieList == (otherItem as MovieListHeaderItem).movieList
    }
}