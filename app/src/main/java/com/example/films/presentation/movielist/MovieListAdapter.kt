package com.example.films.presentation.movielist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.films.GlideApp
import com.example.films.R
import com.example.films.data.models.MovieList
import com.example.films.data.models.UserMovieInfo
import com.example.films.presentation.adapter.AdapterItemDiffCallback
import com.example.films.presentation.adapter.AdapterItemViewHolder
import com.example.films.presentation.adapter.items.AdapterItem
import com.example.films.presentation.adapter.items.MovieListHeaderItem
import com.example.films.presentation.adapter.items.MovieListMovieItem
import com.example.films.utils.formatCreatedDate
import com.example.films.utils.formatReleaseYear
import kotlinx.android.synthetic.main.row_movie_list_header.view.*
import kotlinx.android.synthetic.main.row_movie_list_movie.view.*
import kotlinx.android.synthetic.main.row_search_movie.view.imagePoster
import kotlinx.android.synthetic.main.row_search_movie.view.textMovieCast
import kotlinx.android.synthetic.main.row_search_movie.view.textMovieTitle


class MovieListAdapter(private val movieListCallbacks: MovieListCallbacks) :
    RecyclerView.Adapter<AdapterItemViewHolder>() {

    private val items = mutableListOf<AdapterItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterItemViewHolder {
        val rowView = LayoutInflater.from(parent.context).inflate(viewType, parent, false)
        return when (viewType) {
            R.layout.row_movie_list_movie -> MovieListMovieViewHolder(rowView, movieListCallbacks)
            R.layout.row_movie_list_header -> MovieListHeaderViewHolder(rowView)
            else -> throw IllegalArgumentException("MovieListAdapter: Unknown view type: $viewType")
        }
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: AdapterItemViewHolder, position: Int) =
        holder.bindItem(items[position])

    override fun getItemViewType(position: Int): Int = items[position].getViewType()

    fun setMovieList(movieList: MovieList) {
        val newItems = mutableListOf<AdapterItem>()
        if (movieList.movies.isNotEmpty()) {
            newItems.add(MovieListHeaderItem(movieList))
        }
        for (movie in movieList.movies) {
            newItems.add(MovieListMovieItem(movie))
        }
        val diffResult = DiffUtil.calculateDiff(AdapterItemDiffCallback(items, newItems))
        items.clear()
        items.addAll(newItems)
        diffResult.dispatchUpdatesTo(this)
    }

    fun getMovieId(position: Int): Int {
        val item = items[position]
        return if (item is MovieListMovieItem) {
            item.movie.id
        } else {
            -1
        }
    }

    fun remove(position: Int) {
        items.removeAt(position)
        notifyItemRemoved(position)
    }
}

class MovieListHeaderViewHolder(itemView: View) : AdapterItemViewHolder(itemView) {
    override fun bindItem(item: AdapterItem) {
        val listItem = item as MovieListHeaderItem
        itemView.apply {
            progressWatched.progress =
                listItem.movieList.watchedCount / listItem.movieList.movies.size
            textWatchedCount.text = context.getString(
                R.string.watched_count,
                listItem.movieList.watchedCount,
                listItem.movieList.movies.size
            )
            textCreatedDate.text = formatCreatedDate(listItem.movieList.createdDate)
        }
    }
}

class MovieListMovieViewHolder(itemView: View, private val movieListCallbacks: MovieListCallbacks) :
    AdapterItemViewHolder(itemView) {

    override fun bindItem(item: AdapterItem) {
        val movieItem = item as MovieListMovieItem
        itemView.apply {
            textMovieTitle.text = context.getString(
                R.string.format_movie_title_year,
                movieItem.movie.title,
                formatReleaseYear(movieItem.movie.releaseDate)
            )
            textMovieCast.text = movieItem.movie.cast.joinToString { it }
            GlideApp.with(imagePoster).load(movieItem.movie.poster).into(imagePoster)
            btnOptions.setOnClickListener { showOptions(movieItem.movie.id, movieItem.movie.userMovieInfo) }
        }
    }

    private fun showOptions(movieId: Int, userMovieInfo: UserMovieInfo) {
        val popup = PopupMenu(itemView.context, itemView.btnOptions)
        popup.menuInflater.inflate(R.menu.movie_list_movie_options, popup.menu)
        val toggleItem = popup.menu.findItem(R.id.option_toggle_watched)
        toggleItem.setTitle(if (userMovieInfo.watched) R.string.movie_set_unwatched else R.string.movie_set_watched)
        popup.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.option_delete -> {
                    movieListCallbacks.deleteMovie(movieId)
                    true
                }
                R.id.option_toggle_watched -> {
                    movieListCallbacks.toggleMovieWatched(movieId)
                    true
                }
                else -> false
            }
        }
        popup.show()
    }
}

interface MovieListCallbacks {
    fun deleteMovie(movieId: Int)
    fun toggleMovieWatched(movieId: Int)
}