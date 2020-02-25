package com.example.films.presentation.userlists

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.core.graphics.drawable.DrawableCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.films.GlideApp
import com.example.films.R
import com.example.films.data.models.MovieList
import com.example.films.data.models.UsersMovieLists
import com.example.films.presentation.adapter.AdapterItemDiffCallback
import com.example.films.presentation.adapter.AdapterItemViewHolder
import com.example.films.presentation.adapter.SectionLabelViewHolder
import com.example.films.presentation.adapter.items.*
import com.example.films.utils.formatReleaseDate
import com.example.films.utils.lightenColor
import kotlinx.android.synthetic.main.row_reminders.view.*
import kotlinx.android.synthetic.main.row_user_list.view.*

class UserListsAdapter(private val callbacks: UserListsCallbacks) : RecyclerView.Adapter<AdapterItemViewHolder>() {

    private val items = mutableListOf<AdapterItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterItemViewHolder {
        val rowView = LayoutInflater.from(parent.context).inflate(viewType, parent, false)
        return when (viewType) {
            R.layout.row_reminders -> RemindersViewHolder(rowView, callbacks)
            R.layout.row_section_label -> SectionLabelViewHolder(rowView)
            R.layout.row_user_list -> UserListViewHolder(rowView, callbacks)
            R.layout.row_empty_user_list -> EmptyUserListViewHolder(rowView)
            else -> throw IllegalArgumentException("UserListsAdapter: Unknown viewType: $viewType")
        }
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: AdapterItemViewHolder, position: Int) = holder.bindItem(items[position])

    override fun getItemViewType(position: Int): Int = items[position].getViewType()

    fun setUserLists(userLists: UsersMovieLists) {
        val newItems = mutableListOf<AdapterItem>()
        newItems.add(RemindersItem(userLists.nextReminder))
        newItems.add(SectionLabelItem(R.string.label_lists))

        if(userLists.movieLists.isNotEmpty()) {
            userLists.movieLists.forEach { newItems.add(MovieListItem(it)) }
        } else{
            newItems.add(EmptyUserListItem())
        }

        val diffResult = DiffUtil.calculateDiff(AdapterItemDiffCallback(items, newItems))
        items.clear()
        items.addAll(newItems)
        diffResult.dispatchUpdatesTo(this)
    }
}

class RemindersViewHolder(itemView: View, private val callbacks: UserListsCallbacks) : AdapterItemViewHolder(itemView) {
    override fun bindItem(item: AdapterItem) {
        val remindersItem = item as RemindersItem
        itemView.apply {
            groupContent.visibility = if (remindersItem.reminder == null) View.GONE else View.VISIBLE
            textNoReminder.visibility = if (remindersItem.reminder == null) View.VISIBLE else View.GONE
            if (remindersItem.reminder != null) {
                textMovieTitle.text = remindersItem.reminder.movie.title
                textMovieDescription.text = remindersItem.reminder.movie.description
                textReleaseDate.text = formatReleaseDate(remindersItem.reminder.movie.releaseDate)
                btnTrailer.visibility = if (remindersItem.reminder.movie.trailerUrl.isEmpty()) View.GONE else View.VISIBLE
                btnTrailer.setOnClickListener { callbacks.onTrailer(remindersItem.reminder.movie.trailerUrl)}
                GlideApp.with(imgPoster).load(remindersItem.reminder.movie.poster).into(imgPoster)
            }
            btnViewAll.setOnClickListener { callbacks.onViewAllReminders() }
        }
    }
}

class UserListViewHolder(itemView: View, private val callbacks: UserListsCallbacks) : AdapterItemViewHolder(itemView) {
    override fun bindItem(item: AdapterItem) {
        val movieListItem = item as MovieListItem
        val color = Color.parseColor(movieListItem.movieList.color)
        itemView.apply {
            textListTitle.text = movieListItem.movieList.title
            textMovieCount.text = resources.getQuantityString(
                R.plurals.number_movies,
                movieListItem.movieList.movies.size,
                movieListItem.movieList.movies.size
            )
            DrawableCompat.setTint(DrawableCompat.wrap(imageMovieList.background), color)
            DrawableCompat.setTint(DrawableCompat.wrap(imageMovieList.drawable.mutate()), lightenColor(color, 0.25f))
            setOnClickListener { callbacks.onMovieList(movieListItem.movieList) }
        }
    }
}

class EmptyUserListViewHolder(itemView: View) : AdapterItemViewHolder(itemView) {
    override fun bindItem(item: AdapterItem) {
    }
}

interface UserListsCallbacks{
    fun onTrailer(url: String)
    fun onViewAllReminders()
    fun onMovieList(movieList: MovieList)
}