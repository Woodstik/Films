package com.example.films.presentation.adapter.items

import com.example.films.R
import com.example.films.data.models.Post

class PostItem(val post: Post) : AdapterItem {

    override fun getViewType(): Int = R.layout.row_post

    override fun isSameItem(otherItem: AdapterItem): Boolean {
        return isSameType(otherItem) && post.id == (otherItem as PostItem).post.id
    }

    override fun isSameContent(otherItem: AdapterItem): Boolean {
        return post == (otherItem as PostItem).post
    }
}
