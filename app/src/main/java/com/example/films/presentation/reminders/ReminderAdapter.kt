package com.example.films.presentation.reminders

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.films.GlideApp
import com.example.films.R
import com.example.films.data.models.MovieReminder
import com.example.films.presentation.adapter.AdapterItemDiffCallback
import com.example.films.presentation.adapter.AdapterItemViewHolder
import com.example.films.presentation.adapter.items.AdapterItem
import com.example.films.presentation.adapter.items.ReminderItem
import com.example.films.utils.formatReminderDate
import kotlinx.android.synthetic.main.row_reminder.view.*

class ReminderAdapter(private val callbacks: ReminderCallbacks) :RecyclerView.Adapter<AdapterItemViewHolder>() {

    private val items = mutableListOf<AdapterItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterItemViewHolder {
        return ReminderViewHolder(callbacks, LayoutInflater.from(parent.context).inflate(R.layout.row_reminder, parent, false))
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: AdapterItemViewHolder, position: Int) = holder.bindItem(items[position])

    fun setReminders(reminders: List<MovieReminder>){
        val newItems = mutableListOf<AdapterItem>()
        for (reminder in reminders) {
            newItems.add(ReminderItem(reminder))
        }
        val diffResult = DiffUtil.calculateDiff(AdapterItemDiffCallback(items, newItems))
        items.clear()
        items.addAll(newItems)
        diffResult.dispatchUpdatesTo(this)
    }
}

class ReminderViewHolder(
    private val callbacks: ReminderCallbacks,
    itemView: View
) : AdapterItemViewHolder(itemView){

    override fun bindItem(item: AdapterItem) {
        val reminderItem = item as ReminderItem
        itemView.apply {
            textMovieTitle.text = reminderItem.movieReminder.movie.title
            textReminderDate.text = formatReminderDate(reminderItem.movieReminder.remindDate)
            GlideApp.with(imagePoster).load(reminderItem.movieReminder.movie.poster).into(imagePoster)
            btnDelete.setOnClickListener { callbacks.onDelete(reminderItem.movieReminder) }
        }
        itemView.setOnClickListener { callbacks.onClickReminder(reminderItem.movieReminder) }
    }
}

interface ReminderCallbacks{
    fun onClickReminder(reminder: MovieReminder)
    fun onDelete(reminder: MovieReminder)
}