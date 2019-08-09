package com.example.films.presentation.userlists

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.films.GlideApp
import com.example.films.R
import com.example.films.data.models.UsersMovieLists
import com.example.films.presentation.adapter.AdapterItemDiffCallback
import com.example.films.presentation.adapter.AdapterItemViewHolder
import com.example.films.presentation.adapter.SectionLabelViewHolder
import com.example.films.presentation.adapter.items.AdapterItem
import com.example.films.presentation.adapter.items.MovieListItem
import com.example.films.presentation.adapter.items.RemindersItem
import com.example.films.presentation.adapter.items.SectionLabelItem
import com.example.films.utils.formatReleaseDate
import kotlinx.android.synthetic.main.row_reminders.view.*
import kotlinx.android.synthetic.main.row_user_list.view.*

class UserListsAdapter : RecyclerView.Adapter<AdapterItemViewHolder>() {

    private val items = mutableListOf<AdapterItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterItemViewHolder {
        val rowView = LayoutInflater.from(parent.context).inflate(viewType, parent, false)
        return when (viewType) {
            R.layout.row_reminders -> RemindersViewHolder(rowView)
            R.layout.row_section_label -> SectionLabelViewHolder(rowView)
            R.layout.row_user_list -> UserListViewHolder(rowView)
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

        userLists.movieLists.forEach { newItems.add(MovieListItem(it)) }

        val diffResult = DiffUtil.calculateDiff(AdapterItemDiffCallback(items, newItems))
        items.clear()
        items.addAll(newItems)
        diffResult.dispatchUpdatesTo(this)
    }
}

class RemindersViewHolder(itemView: View) : AdapterItemViewHolder(itemView) {
    override fun bindItem(item: AdapterItem) {
        val remindersItem = item as RemindersItem
        itemView.apply {
            textMovieTitle.text = remindersItem.reminder.movie.title
            textMovieDescription.text = remindersItem.reminder.movie.description
            textReleaseDate.text = formatReleaseDate(remindersItem.reminder.movie.releaseDate)
            GlideApp.with(imgPoster).load(remindersItem.reminder.movie.poster).into(imgPoster)
        }
    }
}

class UserListViewHolder(itemView: View) : AdapterItemViewHolder(itemView) {
    override fun bindItem(item: AdapterItem) {
        val movieListItem = item as MovieListItem
        itemView.apply {
            textListTitle.text = movieListItem.movieList.title
            textCount.text = movieListItem.movieList.movies.size.toString()
            textAuthor.text = movieListItem.movieList.author
        }
    }
}
