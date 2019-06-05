package com.example.films.presentation.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.films.GlideApp
import com.example.films.R
import com.example.films.data.Movie
import com.example.films.utils.formatReleaseDate
import kotlinx.android.synthetic.main.row_new_release.view.*

class NewReleasesAdapter : RecyclerView.Adapter<NewReleaseViewHolder>() {

    private val items = mutableListOf<Movie>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewReleaseViewHolder {
        return NewReleaseViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.row_new_release,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: NewReleaseViewHolder, position: Int) {
        holder.bindMovie(items[position], itemCount)
    }

    fun addItems(newReleases: List<Movie>) {
        items.clear()
        items.addAll(newReleases)
        notifyDataSetChanged()
    }
}

class NewReleaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bindMovie(movie: Movie, total: Int) {
        itemView.apply {
            textTitle.text = movie.title
            textDescription.text = movie.description
            textReleaseDate.text = formatReleaseDate(movie.releaseDate)
            textNumber.text = context.getString(R.string.format_item_number, adapterPosition + 1, total)
            GlideApp.with(imageBackdrop)
                .load(movie.backdrop)
                .into(imageBackdrop)
        }
    }
}
