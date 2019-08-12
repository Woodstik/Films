package com.example.films.data.sources.repositories

import com.example.films.data.models.Post
import com.example.films.data.sources.PostDataSource
import com.example.films.data.sources.remote.PostService
import io.reactivex.Flowable

class PostRepository(private val postService: PostService) : PostDataSource {
    override fun getPosts(): Flowable<List<Post>> {
        return postService.getPosts()
            .toFlowable()
    }

}
