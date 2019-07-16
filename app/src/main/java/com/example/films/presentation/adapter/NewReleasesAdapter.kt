package com.example.films.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.films.GlideApp
import com.example.films.R
import com.example.films.data.models.Movie
import com.example.films.utils.formatReleaseDate
import kotlinx.android.synthetic.main.row_new_release.view.*

class NewReleasesAdapter(private val callbacks: NewReleaseCallbacks) : RecyclerView.Adapter<NewReleaseViewHolder>() {

    private val items = mutableListOf<Movie>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewReleaseViewHolder {
        return NewReleaseViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.row_new_release,
                parent,
                false
            ),
            callbacks
        )
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: NewReleaseViewHolder, position: Int) {
        holder.bindMovie(items[position])
    }

    fun addItems(newReleases: List<Movie>) {
        items.clear()
        items.addAll(newReleases)
        notifyDataSetChanged()
    }
}

class NewReleaseViewHolder(itemView: View, private val callbacks: NewReleaseCallbacks) :
    RecyclerView.ViewHolder(itemView) {

    fun bindMovie(movie: Movie) {
        itemView.apply {
            textTitle.text = movie.title
            textReleaseDate.text = formatReleaseDate(movie.releaseDate)
            btnAddToList.setOnClickListener { callbacks.onAddToList(movie) }
            btnTrailer.setOnClickListener { callbacks.onTrailer("http://www.trailer.com") }
            btnShare.setOnClickListener { callbacks.onShare(movie) }
            setOnClickListener { callbacks.onClick(movie) }
            GlideApp.with(imageBackdrop)
                .load(movie.backdrop)
                .into(imageBackdrop)
        }
    }
}

interface NewReleaseCallbacks {
    fun onAddToList(movie: Movie)
    fun onTrailer(url: String)
    fun onShare(movie: Movie)
    fun onClick(movie: Movie)
}
