package com.example.films.data.sources

import com.example.films.data.models.Post
import io.reactivex.Flowable

interface PostDataSource {
    fun getPosts(): Flowable<List<Post>>
}
