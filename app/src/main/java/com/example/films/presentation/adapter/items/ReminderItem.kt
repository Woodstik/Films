package com.example.films.presentation.adapter.items

import com.example.films.R
import com.example.films.data.models.MovieReminder

class ReminderItem(val movieReminder: MovieReminder) : AdapterItem {

    override fun getViewType(): Int = R.layout.row_reminder

    override fun isSameItem(otherItem: AdapterItem): Boolean {
        return isSameType(otherItem) && movieReminder.id == (otherItem as ReminderItem).movieReminder.id
    }

    override fun isSameContent(otherItem: AdapterItem): Boolean {
        return movieReminder == (otherItem as ReminderItem).movieReminder
    }
}