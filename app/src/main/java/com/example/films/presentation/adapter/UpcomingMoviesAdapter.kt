package com.example.films.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.films.GlideApp
import com.example.films.R
import com.example.films.data.models.Movie
import com.example.films.utils.formatReleaseDateShort
import kotlinx.android.synthetic.main.row_upcoming_movie.view.*

class UpcomingMoviesAdapter(private val callbacks: UpcomingCallbacks) :
    RecyclerView.Adapter<UpcomingMovieViewHolder>() {

    private val items = mutableListOf<Movie>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UpcomingMovieViewHolder {
        return UpcomingMovieViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.row_upcoming_movie,
                parent,
                false
            ),
            callbacks
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

class UpcomingMovieViewHolder(itemView: View, private val callbacks: UpcomingCallbacks) :
    RecyclerView.ViewHolder(itemView) {

    fun bindMovie(movie: Movie) {
        itemView.apply {
            textReleaseDate.text = formatReleaseDateShort(movie.releaseDate)
            btnReminder.setOnClickListener { callbacks.onRemind(movie) }
            setOnClickListener { callbacks.onClick(movie)}
            GlideApp.with(imagePoster)
                .load(movie.poster)
                .into(imagePoster)
        }
    }
}

interface UpcomingCallbacks {
    fun onClick(movie: Movie)
    fun onRemind(movie: Movie)
}
