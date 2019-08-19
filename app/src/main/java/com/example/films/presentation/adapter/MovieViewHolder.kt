package com.example.films.presentation.adapter

import android.view.View
import com.example.films.GlideApp
import com.example.films.R
import com.example.films.presentation.adapter.items.AdapterItem
import com.example.films.presentation.adapter.items.MovieItem
import com.example.films.utils.formatReleaseYear
import kotlinx.android.synthetic.main.row_movie.view.*

class MovieViewHolder(itemView: View) : AdapterItemViewHolder(itemView) {
    override fun bindItem(item: AdapterItem) {
        val movieItem = item as MovieItem
        itemView.apply {
            textMovieTitle.text = context.getString(R.string.format_movie_title_year, movieItem.movie.title, formatReleaseYear(movieItem.movie.releaseDate))
            textMovieCast.text = movieItem.movie.cast.joinToString { it }
            GlideApp.with(imagePoster).load(movieItem.movie.poster).into(imagePoster)
        }
    }
}
