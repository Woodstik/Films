package com.example.films.presentation.adapter.items

import com.example.films.R
import com.example.films.data.models.MovieReminder

class RemindersItem(val reminder: MovieReminder) : AdapterItem {

    override fun getViewType(): Int = R.layout.row_reminders

    override fun isSameItem(otherItem: AdapterItem): Boolean {
        return isSameType(otherItem)
    }

    override fun isSameContent(otherItem: AdapterItem): Boolean {
        return reminder == (otherItem as RemindersItem).reminder
    }
}
