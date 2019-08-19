package com.example.films.presentation.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.films.R
import com.example.films.data.models.Movie
import com.example.films.presentation.adapter.AdapterItemDiffCallback
import com.example.films.presentation.adapter.AdapterItemViewHolder
import com.example.films.presentation.adapter.MovieViewHolder
import com.example.films.presentation.adapter.items.AdapterItem
import com.example.films.presentation.adapter.items.MovieItem

class SearchAdapter : RecyclerView.Adapter<AdapterItemViewHolder>() {

    private val items = mutableListOf<AdapterItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterItemViewHolder {
        return MovieViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.row_movie, parent, false))
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: AdapterItemViewHolder, position: Int) = holder.bindItem(items[position])

    fun setMovies(movies: List<Movie>) {
        val newItems = mutableListOf<AdapterItem>()
        for (movie in movies) {
            newItems.add(MovieItem(movie))
        }
        val diffResult = DiffUtil.calculateDiff(AdapterItemDiffCallback(items, newItems))
        items.clear()
        items.addAll(newItems)
        diffResult.dispatchUpdatesTo(this)
    }
}
