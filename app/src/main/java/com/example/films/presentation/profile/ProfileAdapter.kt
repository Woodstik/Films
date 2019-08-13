package com.example.films.presentation.profile

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.films.R
import com.example.films.data.models.Profile
import com.example.films.data.models.ProfileOption
import com.example.films.presentation.adapter.AdapterItemViewHolder
import com.example.films.presentation.adapter.items.AdapterItem
import com.example.films.presentation.adapter.items.ProfileHeaderItem
import com.example.films.presentation.adapter.items.ProfileOptionItem
import com.example.films.utils.formatJoinedDate
import kotlinx.android.synthetic.main.row_profile_header.view.*
import kotlinx.android.synthetic.main.row_profile_option.view.*
import java.util.*

class ProfileAdapter : RecyclerView.Adapter<AdapterItemViewHolder>() {

    private val items = listOf(
        //TODO: Need to remove hardcoded email
        ProfileHeaderItem(Profile(1, "example@gmail.com", Date())),
        ProfileOptionItem(
            ProfileOption(
                R.drawable.ic_option_stats,
                R.string.option_stats_title,
                R.string.option_stats_description
            )
        ),
        ProfileOptionItem(
            ProfileOption(
                R.drawable.ic_option_comment,
                R.string.option_comment_title,
                R.string.option_comment_description
            )
        ),
        ProfileOptionItem(
            ProfileOption(
                R.drawable.ic_option_about,
                R.string.option_about_title,
                R.string.option_about_description
            )
        ),
        ProfileOptionItem(
            ProfileOption(
                R.drawable.ic_option_logout,
                R.string.option_logout_title,
                R.string.option_logout_description
            )
        )
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterItemViewHolder {
        val rowView = LayoutInflater.from(parent.context).inflate(viewType, parent, false)
        return when (viewType) {
            R.layout.row_profile_header -> ProfileHeaderViewHolder(rowView)
            R.layout.row_profile_option -> ProfileOptionViewHolder(rowView)
            else -> throw IllegalArgumentException("ProfileAdapter unknown view type: $viewType")
        }
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: AdapterItemViewHolder, position: Int) {
        holder.bindItem(items[position])
    }

    override fun getItemViewType(position: Int): Int = items[position].getViewType()
}

class ProfileHeaderViewHolder(itemView: View) : AdapterItemViewHolder(itemView) {
    override fun bindItem(item: AdapterItem) {
        val header = item as ProfileHeaderItem
        itemView.apply {
            textInitial.text = header.profile.email[0].toString().toUpperCase()
            textEmail.text = header.profile.email
            textCreatedDate.text =
                context.getString(R.string.format_date_joined, formatJoinedDate(header.profile.createdDate))
        }
    }
}

class ProfileOptionViewHolder(itemView: View) : AdapterItemViewHolder(itemView) {
    override fun bindItem(item: AdapterItem) {
        val optionItem = item as ProfileOptionItem
        itemView.apply {
            imageOption.setImageResource(optionItem.option.icon)
            textOptionTitle.setText(optionItem.option.title)
            textOptionDescription.setText(optionItem.option.description)
        }
    }
}
