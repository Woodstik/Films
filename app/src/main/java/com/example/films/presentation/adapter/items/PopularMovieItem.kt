package com.example.films.presentation.adapter.items

import com.example.films.R
import com.example.films.data.models.Movie

class PopularMovieItem(val movie: Movie) : AdapterItem {

    override fun getViewType(): Int = R.layout.row_popular_movie

    override fun isSameItem(otherItem: AdapterItem): Boolean {
        return isSameType(otherItem) && movie.id == (otherItem as PopularMovieItem).movie.id
    }

    override fun isSameContent(otherItem: AdapterItem): Boolean {
        return movie == (otherItem as PopularMovieItem).movie
    }
}
