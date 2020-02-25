package com.example.films.presentation.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.films.GlideApp
import com.example.films.R
import com.example.films.data.models.Movie
import com.example.films.presentation.adapter.AdapterItemDiffCallback
import com.example.films.presentation.adapter.AdapterItemViewHolder
import com.example.films.presentation.adapter.items.AdapterItem
import com.example.films.presentation.adapter.items.SearchMovieItem
import com.example.films.utils.formatReleaseYear
import kotlinx.android.synthetic.main.row_search_movie.view.*

class SearchAdapter() : RecyclerView.Adapter<AdapterItemViewHolder>() {

    private val items = mutableListOf<AdapterItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterItemViewHolder {
        return SearchMovieViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.row_search_movie, parent, false))
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: AdapterItemViewHolder, position: Int) = holder.bindItem(items[position])

    fun setMovies(movies: List<Movie>) {
        val newItems = mutableListOf<AdapterItem>()
        for (movie in movies) {
            newItems.add(SearchMovieItem(movie))
        }
        val diffResult = DiffUtil.calculateDiff(AdapterItemDiffCallback(items, newItems))
        items.clear()
        items.addAll(newItems)
        diffResult.dispatchUpdatesTo(this)
    }
}

class SearchMovieViewHolder(itemView: View) : AdapterItemViewHolder(itemView) {
    override fun bindItem(item: AdapterItem) {
        val movieItem = item as SearchMovieItem
        itemView.apply {
            textMovieTitle.text = context.getString(R.string.format_movie_title_year, movieItem.movie.title, formatReleaseYear(movieItem.movie.releaseDate))
            textMovieCast.text = movieItem.movie.cast.joinToString { it }
            GlideApp.with(imagePoster).load(movieItem.movie.poster).into(imagePoster)
        }
    }
}