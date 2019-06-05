package com.example.films.presentation.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.films.GlideApp
import com.example.films.R
import com.example.films.data.Movie
import kotlinx.android.synthetic.main.row_upcoming_movie.view.*

class UpcomingMoviesAdapter : RecyclerView.Adapter<UpcomingMovieViewHolder>() {

    private val items = mutableListOf<Movie>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UpcomingMovieViewHolder {
        return UpcomingMovieViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.row_upcoming_movie,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: UpcomingMovieViewHolder, position: Int) {
        holder.bindMovie(items[position])
    }

    fun addItems(upcomingMovies: List<Movie>) {
        items.clear()
        items.addAll(upcomingMovies)
        notifyDataSetChanged()
    }
}

class UpcomingMovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bindMovie(movie: Movie) {
        itemView.apply {
            textRating.text = "${movie.rating}"
            GlideApp.with(imagePoster)
                .load(movie.poster)
                .into(imagePoster)
        }
    }
}
