package com.example.films.presentation.selectlist

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.graphics.drawable.DrawableCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.films.R
import com.example.films.data.models.MovieList
import com.example.films.presentation.adapter.AdapterItemDiffCallback
import com.example.films.presentation.adapter.AdapterItemViewHolder
import com.example.films.presentation.adapter.items.AdapterItem
import com.example.films.presentation.adapter.items.SelectListItem
import com.example.films.utils.lightenColor
import kotlinx.android.synthetic.main.row_select_list.view.*

class SelectListAdapter : RecyclerView.Adapter<AdapterItemViewHolder>() {

    private val items = mutableListOf<AdapterItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterItemViewHolder {
        val rowView = LayoutInflater.from(parent.context).inflate(viewType, parent, false)
        return when (viewType) {
            R.layout.row_select_list -> SelectListViewHolder(rowView)
            else -> throw IllegalArgumentException("SelectListAdapter: Unknown viewType: $viewType")
        }
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: AdapterItemViewHolder, position: Int) {
        holder.bindItem(items[position])
    }

    override fun getItemViewType(position: Int): Int = items[position].getViewType()

    fun setMovieLists(movieList: List<MovieList>){
        val newItems = mutableListOf<AdapterItem>()

        movieList.forEach { newItems.add(SelectListItem(it)) }

        val diffResult = DiffUtil.calculateDiff(AdapterItemDiffCallback(items, newItems))
        items.clear()
        items.addAll(newItems)
        diffResult.dispatchUpdatesTo(this)
    }
}

class SelectListViewHolder(itemView: View) : AdapterItemViewHolder(itemView) {

    override fun bindItem(item: AdapterItem) {
        val selectListItem = item as SelectListItem
        val color = Color.parseColor(selectListItem.movieList.color)
        itemView.apply {
            textListTitle.text = selectListItem.movieList.title
            DrawableCompat.setTint(DrawableCompat.wrap(imageMovieList.background), color)
            DrawableCompat.setTint(DrawableCompat.wrap(imageMovieList.drawable.mutate()), lightenColor(color, 0.25f))
        }
        itemView.setOnClickListener { Toast.makeText(itemView.context, "Selected:${selectListItem.movieList.id}", Toast.LENGTH_SHORT).show() }
    }
}