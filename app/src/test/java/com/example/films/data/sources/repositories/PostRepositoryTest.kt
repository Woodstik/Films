package com.example.films.data.sources.repositories

import com.example.films.data.models.Post
import com.example.films.data.sources.PostDataSource
import com.example.films.data.sources.remote.PostService
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import java.io.IOException
import java.util.*

@RunWith(MockitoJUnitRunner::class)
class PostRepositoryTest {

    @Mock
    private lateinit var postService: PostService
    private lateinit var postDataSource: PostDataSource

    @Before
    fun setUp() {
        postDataSource = PostRepository(postService)
    }

    @Test
    fun getPosts_emptyList() {
        Mockito.`when`(postService.getPosts()).thenReturn(Single.just(emptyList()))
        val testSubscriber = postDataSource.getPosts().test()
        testSubscriber.assertValue { it.isEmpty() }
        testSubscriber.assertComplete()
    }

    @Test
    fun getPosts_successValue() {
        val post = Post("","", Date(), "", "", 1, emptyList())
        Mockito.`when`(postService.getPosts()).thenReturn(Single.just(listOf(post)))
        val testSubscriber = postDataSource.getPosts().test()
        testSubscriber.assertValue { it.contains(post) }
        testSubscriber.assertComplete()
    }

    @Test
    fun getPosts_serviceError() {
        Mockito.`when`(postService.getPosts())
            .thenReturn(Single.error(IOException("Connection error")))
        val testSubscriber = postDataSource.getPosts().test()
        testSubscriber.assertError { it is IOException }
    }
}