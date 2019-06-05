package com.example.films.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.films.adapter.items.AdapterItem

abstract class AdapterItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    abstract fun bindItem(item: AdapterItem)
}
