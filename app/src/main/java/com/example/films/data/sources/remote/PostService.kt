package com.example.films.data.sources.remote

import com.example.films.data.models.Post
import io.reactivex.Single

interface PostService {
    fun getPosts(): Single<List<Post>>
}
