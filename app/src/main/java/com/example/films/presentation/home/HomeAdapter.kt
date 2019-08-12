package com.example.films.presentation.home

import android.graphics.Typeface.BOLD
import android.text.Spannable
import android.text.SpannableString
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.films.GlideApp
import com.example.films.R
import com.example.films.data.models.HomeMovies
import com.example.films.presentation.adapter.*
import com.example.films.presentation.adapter.items.*
import com.example.films.utils.HorizontalSpacingDecoration
import com.example.films.utils.formatPostTime
import kotlinx.android.synthetic.main.row_new_releases.view.*
import kotlinx.android.synthetic.main.row_post.view.*
import kotlinx.android.synthetic.main.row_upcoming_movies.view.*

class HomeAdapter(
    private val newReleaseCallbacks: NewReleaseCallbacks,
    private val upcomingCallbacks: UpcomingCallbacks
) :
    RecyclerView.Adapter<AdapterItemViewHolder>() {

    private val items = mutableListOf<AdapterItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterItemViewHolder {
        val rowView = LayoutInflater.from(parent.context).inflate(viewType, parent, false)
        return when (viewType) {
            R.layout.row_section_label -> SectionLabelViewHolder(rowView)
            R.layout.row_new_releases -> NewReleasesViewHolder(rowView, newReleaseCallbacks)
            R.layout.row_post -> PostViewHolder(rowView)
            R.layout.row_upcoming_movies -> UpcomingMoviesViewHolder(rowView, upcomingCallbacks)
            else -> throw IllegalArgumentException("HomeAdapter: Unknown viewType: $viewType")
        }
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: AdapterItemViewHolder, position: Int) = holder.bindItem(items[position])

    override fun getItemViewType(position: Int): Int = items[position].getViewType()

    fun setMovies(homeMovies: HomeMovies) {
        val (newReleases, upcoming, posts) = homeMovies
        val newItems = mutableListOf<AdapterItem>()

        newItems.add(SectionLabelItem(R.string.label_new_releases))
        newItems.add(NewReleasesItem(newReleases))

        newItems.add(SectionLabelItem(R.string.label_upcoming))
        newItems.add(UpcomingMoviesItem(upcoming))
        newItems.add(SectionLabelItem(R.string.label_movie_subreddit))
        posts.forEach { post -> newItems.add(PostItem(post)) }

        val diffResult = DiffUtil.calculateDiff(AdapterItemDiffCallback(items, newItems))
        items.clear()
        items.addAll(newItems)
        diffResult.dispatchUpdatesTo(this)
    }
}

class NewReleasesViewHolder(itemView: View, callbacks: NewReleaseCallbacks) : AdapterItemViewHolder(itemView) {

    private var adapter = NewReleasesAdapter(callbacks)

    init {
        itemView.listNewReleases.layoutManager =
            LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL, false)
        itemView.listNewReleases.adapter = adapter
        itemView.listNewReleases.addItemDecoration(
            HorizontalSpacingDecoration(
                itemView.context.resources.getDimensionPixelSize(
                    R.dimen.spacing_normal
                )
            )
        )
        LinearSnapHelper().attachToRecyclerView(itemView.listNewReleases)
    }

    override fun bindItem(item: AdapterItem) {
        adapter.addItems((item as NewReleasesItem).newReleases)

    }
}

class PostViewHolder(itemView: View) : AdapterItemViewHolder(itemView) {
    override fun bindItem(item: AdapterItem) {
        val postItem = item as PostItem
        val info = itemView.resources.getQuantityString(
            R.plurals.number_comments_source,
            postItem.post.comments.size,
            postItem.post.comments.size, postItem.post.source
        )
        val infoSpannable = SpannableString(info)
        infoSpannable.setSpan(StyleSpan(BOLD), 0, info.indexOf("·"), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        itemView.apply {
            textTitle.text = postItem.post.title
            textInfo.text = infoSpannable
            textTime.text = formatPostTime(postItem.post.createdDate)
            GlideApp.with(imageThumbnail).load(postItem.post.thumbnail).centerCrop().into(imageThumbnail)
        }
    }
}

class UpcomingMoviesViewHolder(itemView: View, callbacks: UpcomingCallbacks) : AdapterItemViewHolder(itemView) {

    private var adapter = UpcomingMoviesAdapter(callbacks)

    init {
        itemView.listUpcomingMovies.also {
            it.layoutManager = LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL, false)
            it.adapter = adapter
            it.addItemDecoration(HorizontalSpacingDecoration(itemView.context.resources.getDimensionPixelSize(R.dimen.spacing_normal)))
        }
        LinearSnapHelper().attachToRecyclerView(itemView.listUpcomingMovies)
    }

    override fun bindItem(item: AdapterItem) {
        adapter.addItems((item as UpcomingMoviesItem).upcomingMovies)
    }
}
