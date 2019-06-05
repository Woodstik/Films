package com.example.films.presentation.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IntegerRes
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.films.GlideApp
import com.example.films.R
import com.example.films.adapter.AdapterItemDiffCallback
import com.example.films.adapter.AdapterItemViewHolder
import com.example.films.adapter.items.*
import com.example.films.data.Movie
import kotlinx.android.synthetic.main.row_home_label.view.*
import kotlinx.android.synthetic.main.row_new_releases.view.*
import kotlinx.android.synthetic.main.row_popular_movie.view.*
import kotlinx.android.synthetic.main.row_upcoming_movies.view.*

class HomeAdapter : RecyclerView.Adapter<AdapterItemViewHolder>() {

    private val items = mutableListOf<AdapterItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterItemViewHolder {
        val rowView = LayoutInflater.from(parent.context).inflate(viewType, parent, false)
        return when (viewType) {
            R.layout.row_home_label -> HomeLabelViewHolder(rowView)
            R.layout.row_new_releases -> NewReleasesViewHolder(rowView)
            R.layout.row_popular_movie -> PopularMovieViewHolder(rowView)
            R.layout.row_upcoming_movies -> UpcomingMoviesViewHolder(rowView)
            else -> throw IllegalArgumentException("HomeAdapter: Unknown viewType: $viewType")
        }
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: AdapterItemViewHolder, position: Int) = holder.bindItem(items[position])

    override fun getItemViewType(position: Int): Int = items[position].getViewType()

    @IntegerRes
    fun getItemSpan(position: Int): Int {
        return items[position].getSpan()
    }

    fun addMovies(newReleases: List<Movie>, upcoming: List<Movie>, popularMovies: List<Movie>) {
        val newItems = mutableListOf<AdapterItem>()

        newItems.add(HomeLabelItem(R.string.label_new_releases))
        newItems.add(NewReleasesItem(newReleases))

        newItems.add(HomeLabelItem(R.string.label_upcoming))
        newItems.add(UpcomingMoviesItem(upcoming))
        newItems.add(HomeLabelItem(R.string.label_popular))
        popularMovies.forEach { popularMovie -> newItems.add(
            PopularMovieItem(
                popularMovie
            )
        ) }

        val diffResult = DiffUtil.calculateDiff(AdapterItemDiffCallback(items, newItems))
        items.clear()
        items.addAll(newItems)
        diffResult.dispatchUpdatesTo(this)
    }
}

class HomeLabelViewHolder(itemView: View) : AdapterItemViewHolder(itemView) {
    override fun bindItem(item: AdapterItem) {
        val homeLabelItem = item as HomeLabelItem
        itemView.textLabel.setText(homeLabelItem.label)
    }
}

class NewReleasesViewHolder(itemView: View) : AdapterItemViewHolder(itemView) {

    private var adapter = NewReleasesAdapter()

    init {
        itemView.listNewReleases.layoutManager =
            LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL, false)
        itemView.listNewReleases.adapter = adapter
        LinearSnapHelper().attachToRecyclerView(itemView.listNewReleases)
    }

    override fun bindItem(item: AdapterItem) {
        adapter.addItems((item as NewReleasesItem).newReleases)

    }
}

class PopularMovieViewHolder(itemView: View) : AdapterItemViewHolder(itemView) {
    override fun bindItem(item: AdapterItem) {
        val popularMovieItem = item as PopularMovieItem
        GlideApp.with(itemView.imagePoster)
            .load(popularMovieItem.movie.poster)
            .into(itemView.imagePoster)
    }
}

class UpcomingMoviesViewHolder(itemView: View) : AdapterItemViewHolder(itemView) {

    private var adapter = UpcomingMoviesAdapter()

    init {
        itemView.listUpcomingMovies.layoutManager =
            LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL, false)
        itemView.listUpcomingMovies.adapter = adapter
        LinearSnapHelper().attachToRecyclerView(itemView.listUpcomingMovies)
    }

    override fun bindItem(item: AdapterItem) {
        adapter.addItems((item as UpcomingMoviesItem).upcomingMovies)
    }
}
